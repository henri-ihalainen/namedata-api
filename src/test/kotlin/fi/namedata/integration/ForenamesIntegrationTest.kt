package fi.namedata.integration

import fi.namedata.model.Forename
import fi.namedata.service.ForenameDto
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
class ForenamesIntegrationTest : AbstractIntegrationTest() {

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
                Forename(name = "Mirka", maleTotal = 10, femaleTotal = 10),
                Forename(name = "Maria", total = 200, femaleTotal = 100),
                Forename(name = "Anu", femaleTotal = 110),
                Forename(name = "Pirjo", femaleFirstName = 90),
                Forename(name = "Matti", maleTotal = 100),
                Forename(name = "John", maleFirstName = 20),
                Forename(name = "Bar", maleOtherNames = 30),
                Forename(
                        name = "Foo",
                        total = 1,
                        femaleTotal = 1,
                        femaleFirstName = 10,
                        femaleOtherNames = 30,
                        maleTotal = 1,
                        maleFirstName = 20,
                        maleOtherNames = 1
                )
        )).blockLast()
    }

    @Test
    fun `empty query returns all names`() {
        client.get().uri("/forenames").exchange()
                .expectStatus().isOk
                .expectBodyList<ForenameDto>().hasSize(8)
    }

    @Test
    fun `empty query sorts by total`() {
        client.get().uri("/forenames").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Maria")
    }

    @Test
    fun `invalid sort term sorts by total`() {
        client.get().uri("/forenames?sortBy=random").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Maria")
    }

    @Test
    fun `sort by name sorts in ascending order`() {
        client.get().uri("/forenames?sortBy=name").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Anu")
    }

    @Test
    fun `sorting by number properties sorts in descending order`() {
        client.get().uri("/forenames?sortBy=total").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Maria")

        client.get().uri("/forenames?sortBy=femaleTotal").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Anu")

        client.get().uri("/forenames?sortBy=femaleFirstName").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Pirjo")

        client.get().uri("/forenames?sortBy=femaleOtherNames").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Foo")

        client.get().uri("/forenames?sortBy=maleTotal").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Matti")

        client.get().uri("/forenames?sortBy=maleFirstName").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("John")

        client.get().uri("/forenames?sortBy=maleOtherNames").exchange()
                .expectStatus().isOk
                .expectBody().jsonPath("$[0].name").isEqualTo("Bar")
    }

    @Test
    fun `model is mapped to dto correctly`() {
        client.get().uri("/forenames").exchange()
                .expectStatus().isOk
                .expectBodyList<ForenameDto>().contains(ForenameDto(
                        name = "Foo",
                        total = 1,
                        femaleTotal = 1,
                        femaleFirstName = 10,
                        femaleOtherNames = 30,
                        maleTotal = 1,
                        maleFirstName = 20,
                        maleOtherNames = 1
                ))
    }
}