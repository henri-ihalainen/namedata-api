package fi.namedata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.namedata.model.Forename
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile("default")
@Component
class DBInit(val mongoTemplate: MongoTemplate, val objectMapper: ObjectMapper) {

    @PostConstruct
    fun init() {
        val map = HashMap<String, Forename>()

        addResource("data/male-first.json", map) { dto, count -> dto.maleFirstCount = count }
        addResource("data/male-others.json", map) { dto, count -> dto.maleOtherCount = count }
        addResource("data/male-all.json", map) { dto, count -> dto.maleAllCount = count }
        addResource("data/female-first.json", map) { dto, count -> dto.femaleFirstCount = count }
        addResource("data/female-others.json", map) { dto, count -> dto.femaleOtherCount = count }
        addResource("data/female-all.json", map) { dto, count -> dto.femaleAllCount = count }

        mongoTemplate.insertAll(map.values)
    }

    private fun addResource(resource: String,
                            map: HashMap<String, Forename>,
                            setCount: (Forename, Int) -> Unit) {
        val namesResource = ClassPathResource(resource)
        val nameDtos = objectMapper.readValue<List<NameResourceDto>>(namesResource.inputStream)
        mapResources(map, nameDtos, setCount)
    }

    private fun mapResources(map: MutableMap<String, Forename>,
                             resourceDtos: List<NameResourceDto>,
                             setCount: (Forename, Int) -> Unit) {
        resourceDtos.forEach({
            var dto = map[it.name]
            if (dto != null) {
                setCount(dto, it.count)
            } else {
                dto = Forename(name = it.name)
                setCount(dto, it.count)
                map[it.name] = dto
            }
        })
    }

    private data class NameResourceDto(val name: String, val count: Int)
}

