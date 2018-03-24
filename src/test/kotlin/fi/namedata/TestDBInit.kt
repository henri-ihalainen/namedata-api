package fi.namedata

import fi.namedata.model.Forename
import fi.namedata.repository.NameRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile("test")
@Component
class TestDBInit(val repository: NameRepository) {
    @PostConstruct
    fun init() {
        repository.saveAll(mutableListOf(
                Forename(name = "Matti", maleAllCount = 100),
                Forename(name = "Mirka", maleAllCount = 10, femaleAllCount = 10),
                Forename(name = "Maria", femaleAllCount = 100),
                Forename(name = "Anu", femaleAllCount = 90),
                Forename(name = "Pirjo", femaleFirstCount = 90),
                Forename(name = "John", maleFirstCount = 20),
                Forename(name = "Foo", femaleFirstCount = 10, maleFirstCount = 20)
        )).subscribe()
    }
}