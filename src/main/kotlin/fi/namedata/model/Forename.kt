package fi.namedata.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Forename(
        @Id
        val id: ObjectId? = null,
        val name: String,
        val maleFirstCount: Int = 0,
        val maleOtherCount: Int = 0,
        val maleAllCount: Int = 0,
        val femaleFirstCount: Int = 0,
        val femaleOtherCount: Int = 0,
        val femaleAllCount: Int = 0
)

@Document
data class NewForename(
        @Id
        val id: ObjectId? = null,
        val name: String,
        val count: Int = 0,
        val female : GenderCount = GenderCount(),
        val male : GenderCount = GenderCount()
)

data class GenderCount(
        val count: Int = 0,
        val firstCount: Int = 0,
        val otherCount: Int = 0
)
