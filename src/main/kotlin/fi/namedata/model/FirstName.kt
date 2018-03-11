package fi.namedata.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class FirstName(
        @Id
        var id: ObjectId? = null,
        var name: String,
        var maleFirstCount: Int? = 0,
        var maleOtherCount: Int? = 0,
        var maleAllCount: Int? = 0,
        var femaleFirstCount: Int? = 0,
        var femaleOtherCount: Int? = 0,
        var femaleAllCount: Int? = 0
)
