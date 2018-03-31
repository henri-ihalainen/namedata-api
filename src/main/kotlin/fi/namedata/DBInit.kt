package fi.namedata

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.namedata.model.Forename
import fi.namedata.model.NewForename
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
        val forenames: Map<String, Forename> = getNameBuilders()
                .groupingBy { it.name }
                .fold({ key, _ -> Forename(name = key) },
                        { _, acc, builder -> builder.append(acc) })

        val forenames2: Map<String, NewForename> = getNameBuilders2()
                .groupingBy { it.name }
                .fold({ key, _ -> NewForename(name = key) },
                        { _, acc, builder -> builder.append(acc) })

        mongoTemplate.insertAll(forenames2.values)
    }

    private fun readResource(resource: String): List<NameCountDto> =
            objectMapper.readValue(ClassPathResource(resource).inputStream)

    private fun getNameBuilders() = (readResource("data/male-first.json")
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

    private fun getNameBuilders2() = (readResource("data/male-first.json")
            .map { builder2(it.name, { f -> f.copy(male = f.male.copy(firstCount = it.count)) }) }
            + readResource("data/male-others.json")
            .map { builder2(it.name, { f -> f.copy(male = f.male.copy(otherCount = it.count)) }) }
            + readResource("data/male-all.json")
            .map { builder2(it.name, { f -> f.copy(count = f.count + it.count, male = f.male.copy(count = it.count)) }) }
            + readResource("data/female-first.json")
            .map { builder2(it.name, { f -> f.copy(female = f.female.copy(firstCount = it.count)) }) }
            + readResource("data/female-others.json")
            .map { builder2(it.name, { f -> f.copy(female = f.female.copy(otherCount = it.count)) }) }
            + readResource("data/female-all.json")
            .map { builder2(it.name, { f -> f.copy(count = f.count + it.count, female = f.female.copy(count = it.count)) }) })

    private fun builder(name: String, append: (Forename) -> Forename) = object {
        val name: String = name
        val append: (Forename) -> Forename = append
    }

    private fun builder2(name: String, append: (NewForename) -> NewForename) = object {
        val name: String = name
        val append: (NewForename) -> NewForename = append
    }

    private data class NameCountDto(val name: String, val count: Int)

    data class ForenameDto(
            val name: String,
            val maleFirstCount: Int = 0,
            val maleOtherCount: Int = 0,
            val maleAllCount: Int = 0,
            val femaleFirstCount: Int = 0,
            val femaleOtherCount: Int = 0,
            val femaleAllCount: Int = 0
    )
}

