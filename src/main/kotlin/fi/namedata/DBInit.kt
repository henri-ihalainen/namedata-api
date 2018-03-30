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
        val forenames: Map<String, Forename> = (readResource("data/male-first.json")
                .map { builder(it.name, { f -> f.copy(maleFirstCount = it.count) }) }
                + readResource("data/male-others.json")
                .map { builder(it.name, { f -> f.copy(maleOtherCount = it.count) }) }
                + readResource("data/male-all.json")
                .map { builder(it.name, { f -> f.copy(maleAllCount = it.count) }) }
                + readResource("data/female-first.json")
                .map { builder(it.name, { f -> f.copy(femaleFirstCount = it.count) }) }
                + readResource("data/female-others.json")
                .map { builder(it.name, { f -> f.copy(femaleOtherCount = it.count) }) }
                + readResource("data/female-all.json")
                .map { builder(it.name, { f -> f.copy(femaleAllCount = it.count) }) })
                .groupingBy { it.name }
                .fold({ key, _ -> Forename(name = key) },
                        { _, acc, builder -> builder.append(acc) })

        mongoTemplate.insertAll(forenames.values)
    }

    private fun readResource(resource: String): List<NameDto> =
            objectMapper.readValue(ClassPathResource(resource).inputStream)


    private data class NameDto(val name: String, val count: Int)

    private fun builder(name: String, append: (Forename) -> Forename) = object {
        val name: String = name
        val append: (Forename) -> Forename = append
    }
}

