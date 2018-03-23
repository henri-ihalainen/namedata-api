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
    fun `find where male all count is between 1000 and 2000`() {
        StepVerifier.create(repository.findByMaleAllCountBetween(1000, 2000).count())
                .expectNext(119)
                .verifyComplete()
    }

    @Test
    fun `find names where all count is greater than 0`() {
        StepVerifier.create(repository.findNamesWithAllCountGreaterThanZero().count())
                .expectNext(11847)
                .verifyComplete()
    }
}