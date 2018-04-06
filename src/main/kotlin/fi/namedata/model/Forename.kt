package fi.namedata.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Forename(
        @Id
        val id: ObjectId? = null,
        val name: String,
        val total: Int = 0,
        val femaleTotal: Int = 0,
        val femaleFirstName: Int = 0,
        val femaleOtherNames: Int = 0,
        val maleTotal: Int = 0,
        val maleFirstName: Int = 0,
        val maleOtherNames: Int = 0

)