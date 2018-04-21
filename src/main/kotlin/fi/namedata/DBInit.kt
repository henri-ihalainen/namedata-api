package fi.namedata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.namedata.model.Forename
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile("production", "dev")
@Component
class DBInit(val mongoTemplate: MongoTemplate, val objectMapper: ObjectMapper) {

    @PostConstruct
    fun init() {
        val forenames: Map<String, Forename> = getNameBuilders()
                .groupingBy { it.name }
                .fold({ key, _ -> Forename(name = key) },
                        { _, acc, builder -> builder.append(acc) })

        mongoTemplate.insertAll(forenames.values)
    }

    private fun readResource(resource: String): List<NameCountDto> =
            objectMapper.readValue(ClassPathResource(resource).inputStream)

    private fun getNameBuilders() = (readResource("data/male-first.json")
            .map { builder(it.name, { f -> f.copy(maleFirstName = it.count) }) }
            + readResource("data/male-others.json")
            .map { builder(it.name, { f -> f.copy(maleOtherNames = it.count) }) }
            + readResource("data/male-all.json")
            .map { builder(it.name, { f -> f.copy(total = f.total + it.count, maleTotal = it.count) }) }
            + readResource("data/female-first.json")
            .map { builder(it.name, { f -> f.copy(femaleFirstName = it.count) }) }
            + readResource("data/female-others.json")
            .map { builder(it.name, { f -> f.copy(femaleOtherNames = it.count) }) }
            + readResource("data/female-all.json")
            .map { builder(it.name, { f -> f.copy(total = f.total + it.count, femaleTotal = it.count) }) })

    private fun builder(name: String, append: (Forename) -> Forename) = object {
        val name: String = name
        val append: (Forename) -> Forename = append
    }

    private data class NameCountDto(val name: String, val count: Int)

}

