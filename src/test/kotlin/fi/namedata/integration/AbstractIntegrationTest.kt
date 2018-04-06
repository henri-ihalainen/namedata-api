package fi.namedata.integration

import fi.namedata.repository.NameRepository
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AbstractIntegrationTest {

    @Autowired
    lateinit var repository: NameRepository

    @Before
    fun clearDb () {
        repository.deleteAll().block()
    }

}