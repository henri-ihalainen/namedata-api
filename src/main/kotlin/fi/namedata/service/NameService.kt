package fi.namedata.service

import fi.namedata.model.Forename
import fi.namedata.model.NewForename
import fi.namedata.repository.NameRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class NameService(val repository: NameRepository) {
    fun getForenames(sortBy: String): Flux<Forename2Dto> = repository.findAll(sortBy)
            .map { it.toDto() }

    fun getForenames(): Flux<Forename2Dto> = repository.findAll()
            .map { it.toDto() }

//    fun getAllNameCounts(): Flux<NameCountDto> = repository.find(Query())
//            .map { NameCountDto(it.name, it.femaleAllCount.plus(it.maleAllCount)) }
//            .sort({o1, o2 ->  o2.count - o1.count})
//
//    fun getFirstNameCounts(): Flux<NameCountDto> = repository
//            .find(Query(Criteria().orOperator(Criteria.where("femaleFirstCount").gt(0), Criteria.where("maleFirstCount").gt(0))))
//            .map { NameCountDto(it.name, it.femaleFirstCount.plus(it.maleFirstCount)) }
//            .sort({o1, o2 ->  o2.count - o1.count})
}

private fun toForenameDto(n: Forename): ForenameDto = ForenameDto(
        n.name,
        n.maleFirstCount,
        n.maleOtherCount,
        n.maleAllCount,
        n.femaleFirstCount,
        n.femaleOtherCount,
        n.femaleAllCount
)

data class ForenameDto(
        val name: String,
        val maleFirstCount: Int = 0,
        val maleOtherCount: Int = 0,
        val maleAllCount: Int = 0,
        val femaleFirstCount: Int = 0,
        val femaleOtherCount: Int = 0,
        val femaleAllCount: Int = 0
)


private fun NewForename.toDto() = Forename2Dto(
        name,
        count,
        GenderCountDto(
                female.count,
                female.firstCount,
                female.otherCount),
        GenderCountDto(
                male.count,
                male.firstCount,
                male.otherCount)
)

data class Forename2Dto(
        val name: String,
        val count: Int,
        val female: GenderCountDto,
        val male: GenderCountDto
)

data class GenderCountDto(
        val totalCount: Int,
        val firstNameCount: Int,
        val otherNamesCount: Int

)

data class NameCountDto(
        val name: String,
        val count: Int
)