package fi.namedata.service

import fi.namedata.model.Forename
import fi.namedata.repository.NameRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*

@Component
class NameService(val repository: NameRepository) {

    fun getForenames(sortTerm: String?): Flux<ForenameDto> = SortInstructions.from(sortTerm)
            .map { getForenames(it.sortBy, it.direction) }
            .orElseGet { getForenames() }

    fun getForenames(sortBy: String, direction: Sort.Direction): Flux<ForenameDto> =
            repository.findAll(sortBy, direction).map { it.toDto() }

    fun getForenames(): Flux<ForenameDto> = getForenames("total", Sort.Direction.DESC)
}

data class SortInstructions(
        val sortBy: String,
        val direction: Sort.Direction
) {
    companion object {
        fun from(sortTerm: String?): Optional<SortInstructions> = Optional.ofNullable(SORT_INSTRUCTIONS[sortTerm])

        private val SORT_INSTRUCTIONS = mapOf(
                Pair("name", SortInstructions("name", Sort.Direction.ASC)),
                Pair("total", SortInstructions("total", Sort.Direction.DESC)),
                Pair("maleTotal", SortInstructions("maleTotal", Sort.Direction.DESC)),
                Pair("femaleFirstName", SortInstructions("femaleFirstName", Sort.Direction.DESC)),
                Pair("femaleOtherNames", SortInstructions("femaleOtherNames", Sort.Direction.DESC)),
                Pair("femaleTotal", SortInstructions("femaleTotal", Sort.Direction.DESC)),
                Pair("maleFirstName", SortInstructions("maleFirstName", Sort.Direction.DESC)),
                Pair("maleOtherNames", SortInstructions("maleOtherNames", Sort.Direction.DESC))
        )
    }

}

private fun Forename.toDto() = ForenameDto(
        name,
        total,
        femaleTotal,
        femaleFirstName,
        femaleOtherNames,
        maleTotal,
        maleFirstName,
        maleOtherNames
)

data class ForenameDto(
        val name: String,
        val total: Int = 0,
        val femaleTotal: Int = 0,
        val femaleFirstName: Int = 0,
        val femaleOtherNames: Int = 0,
        val maleTotal: Int = 0,
        val maleFirstName: Int = 0,
        val maleOtherNames: Int = 0
)