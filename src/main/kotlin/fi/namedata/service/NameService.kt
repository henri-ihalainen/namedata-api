package fi.namedata.service

import fi.namedata.model.FirstName
import fi.namedata.repository.NameRepository
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux

@Component
class NameService(val repository: NameRepository) {
    fun getFirstNames(sortBy: String): Flux<FirstNameDto> = repository.findAll(sortBy)
            .map { toFirstNameDto(it) }

    fun getFirstNames(): Flux<FirstNameDto> = repository.findAll()
            .map { toFirstNameDto(it) }
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