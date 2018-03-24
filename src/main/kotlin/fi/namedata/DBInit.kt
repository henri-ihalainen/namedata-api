package fi.namedata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.namedata.model.FirstName
import fi.namedata.repository.NameRepository
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile("default")
@Component
class DBInit(val nameRepository: NameRepository, val objectMapper: ObjectMapper) {

    @PostConstruct
    fun init() {
        val map = HashMap<String, FirstName>()

        addResource("data/male-first.json", map) { dto, count -> dto.maleFirstCount = count }
        addResource("data/male-others.json", map) { dto, count -> dto.maleOtherCount = count }
        addResource("data/male-all.json", map) { dto, count -> dto.maleAllCount = count }
        addResource("data/female-first.json", map) { dto, count -> dto.femaleFirstCount = count }
        addResource("data/female-others.json", map) { dto, count -> dto.femaleOtherCount = count }
        addResource("data/female-all.json", map) { dto, count -> dto.femaleAllCount = count }

        nameRepository.saveAll(map.values).subscribe()
    }

    private fun addResource(resource: String,
                            map: HashMap<String, FirstName>,
                            setCount: (FirstName, Int) -> Unit) {
        val namesResource = ClassPathResource(resource)
        val nameDtos = objectMapper.readValue<List<NameResourceDto>>(namesResource.inputStream)
        mapResources(map, nameDtos, setCount)
    }

    private fun mapResources(map: MutableMap<String, FirstName>,
                             resourceDtos: List<NameResourceDto>,
                             setCount: (FirstName, Int) -> Unit) {
        resourceDtos.forEach({
            var dto = map[it.name]
            if (dto != null) {
                setCount(dto, it.count)
            } else {
                dto = FirstName(name = it.name)
                setCount(dto, it.count)
                map[it.name] = dto
            }
        })
    }

    private data class NameResourceDto(val name: String, val count: Int)
}

