package fi.namedata.integration

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest
class FirstNamesIntegrationTest {

    @Autowired
    lateinit var context: ApplicationContext

    var client: WebTestClient? = null

    @Before
    fun setup() {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost:8080/")
                .build()
    }

    @Test
    fun `status is ok`() {
        client!!.get().uri("/first-names").exchange()
                .expectStatus().isOk
    }
}