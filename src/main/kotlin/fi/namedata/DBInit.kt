package fi.namedata

import fi.namedata.model.Name
import fi.namedata.model.NameType
import fi.namedata.repository.NameRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.transaction.Transactional

@Component
class DBInit(val nameRepository: NameRepository) {

    @PostConstruct
    @Transactional
    fun init() {
        val testName = Name(name ="Pertti", count = 123, type = NameType.MEN_FIRST)
        nameRepository.save(testName)
        nameRepository.save(testName)
        nameRepository.save(testName)
        println(nameRepository.findAll())
    }
}