package fi.namedata.integration

import fi.namedata.model.Forename
import fi.namedata.service.ForenameDto
import fi.namedata.service.NameCountDto
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList


@RunWith(SpringRunner::class)
@SpringBootTest
class ForenamesIntegrationTest: AbstractIntegrationTest() {

    @Autowired
    lateinit var context: ApplicationContext

    lateinit var client: WebTestClient

    @Before
    fun setup() {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost:8080/")
                .build()

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
    fun `empty query returns all names`() {
        client.get().uri("/forenames").exchange()
                .expectStatus().isOk
                .expectBodyList<ForenameDto>().hasSize(7)
    }

    @Test
    fun `empty query sorts by name`() {
        client.get().uri("/forenames").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Anu")
    }

    @Test
    fun `sortBy sorts in descending order`() {
        client.get().uri("/forenames?sortBy=femaleAllCount").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Maria")
    }

    @Test
    fun `get all sums femaleAllCount and maleAllCount`() {
        client.get().uri("/forenames/all").exchange()
                .expectStatus().isOk
                .expectBodyList<NameCountDto>().contains(NameCountDto("Mirka", 20))
    }

    @Test
    fun `get first shows only names that are first names`() {
        client.get().uri("/forenames/first").exchange()
                .expectStatus().isOk
                .expectBodyList<NameCountDto>()
                .contains(NameCountDto("Foo", 30))
                .contains(NameCountDto("Pirjo", 90))
                .contains(NameCountDto("John", 20))
                .hasSize(3)
    }
}