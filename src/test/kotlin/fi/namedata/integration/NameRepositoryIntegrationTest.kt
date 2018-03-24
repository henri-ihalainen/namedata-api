package fi.namedata.integration

import fi.namedata.repository.NameRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
class NameRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: NameRepository

    @Test
    fun `test find all`() {
        StepVerifier.create(repository.findAll().count())
                .expectNext(3)
                .verifyComplete()
    }
}