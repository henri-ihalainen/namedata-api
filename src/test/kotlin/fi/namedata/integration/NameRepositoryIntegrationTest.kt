package fi.namedata.integration

import fi.namedata.model.Forename
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
class NameRepositoryIntegrationTest: AbstractIntegrationTest() {
    @Before
    fun setUp() {
        repository.saveAll(listOf(
                Forename(name = "Matti", maleAllCount = 100),
                Forename(name = "Mirka", maleAllCount = 10, femaleAllCount = 10),
                Forename(name = "Maria", femaleAllCount = 100),
                Forename(name = "Anu", femaleAllCount = 90),
                Forename(name = "Pirjo", femaleFirstCount = 90),
                Forename(name = "John", maleFirstCount = 20),
                Forename(name = "Foo", femaleFirstCount = 10, maleFirstCount = 20)
        )).blockLast()
    }

    @Test
    fun `test find all`() {
        StepVerifier.create(repository.findAll().count())
                .expectNext(7)
                .verifyComplete()
    }
}