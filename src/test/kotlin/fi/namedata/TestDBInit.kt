package fi.namedata

import fi.namedata.model.FirstName
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Profile("test")
@Component
class TestDBInit(val template: ReactiveMongoTemplate) {
    @PostConstruct
    fun init() {
        template.insertAll(mutableListOf(
                FirstName(name = "Matti", maleAllCount = 100),
                FirstName(name = "Maria", femaleAllCount = 100),
                FirstName(name = "Anu", femaleAllCount = 90)
        )).subscribe()
    }
}