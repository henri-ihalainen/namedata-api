package fi.namedata.integration

import fi.namedata.DBInit
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * This test verifies that [DBInit.init] initializes the database correctly.
 * Expected results are extracted from the real VRK data, available at https://www.avoindata.fi/data/fi/dataset/none
 *
 * Test relies on VRK data updated on 3.5.2018
 */
@RunWith(SpringRunner::class)
@ActiveProfiles("default")
class DBInitIntegrationTest: AbstractIntegrationTest() {
    @Autowired
    lateinit var dbInit: DBInit

    @Test
    fun `db init works`() {
        dbInit.init()

        assertEquals(11847L, repository.findAll().count().block())

        val maria = repository.find(Query(Criteria.where("name").`is`("Maria"))).blockLast()!!

        assertEquals(41, maria.maleAllCount)
        assertEquals(0, maria.maleFirstCount)
        assertEquals(39, maria.maleOtherCount)
        assertEquals(199595, maria.femaleAllCount)
        assertEquals(20271, maria.femaleFirstCount)
        assertEquals(179324, maria.femaleOtherCount)
    }
}