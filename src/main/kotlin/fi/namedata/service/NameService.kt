package fi.namedata.service

import fi.namedata.model.FirstName
import fi.namedata.repository.NameRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class NameService(val repository: NameRepository) {
    fun getFirstNames(sortBy: String = ""): Flux<FirstNameDto> = repository.findAll()
            .sort { o1, o2 -> sortBy(sortBy, o1, o2) }
            .map { toFirstNameDto(it) }
}

fun sortBy(sortBy: String, o1: FirstName, o2: FirstName): Int =
        when (sortBy) {
            "maleAllCount" -> o2.maleAllCount.minus(o1.maleAllCount)
            "maleFirstCount" -> o2.maleFirstCount.minus(o1.maleFirstCount)
            "maleOtherCount" -> o2.maleOtherCount.minus(o1.maleOtherCount)
            "femaleAllCount" -> o2.femaleAllCount.minus(o1.femaleAllCount)
            "femaleFirstCount" -> o2.femaleFirstCount.minus(o1.femaleFirstCount)
            "femaleOtherCount" -> o2.femaleOtherCount.minus(o1.femaleOtherCount)
            "totalCount" -> o2.getTotalCount().compareTo(o1.getTotalCount())
            else -> {
                o1.name.compareTo(o2.name)
            }
        }

private fun toFirstNameDto(n: FirstName): FirstNameDto = FirstNameDto(
        n.name,
        n.maleFirstCount,
        n.maleOtherCount,
        n.maleAllCount,
        n.femaleFirstCount,
        n.femaleOtherCount,
        n.femaleAllCount
)

data class FirstNameDto(
        val name: String,
        val maleFirstCount: Int = 0,
        val maleOtherCount: Int = 0,
        val maleAllCount: Int = 0,
        val femaleFirstCount: Int = 0,
        val femaleOtherCount: Int = 0,
        val femaleAllCount: Int = 0
)