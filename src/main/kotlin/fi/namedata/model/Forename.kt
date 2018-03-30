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
        val femaleAllCount: Int = 0,
        val totalCount: NameCount = NameCount(),
        val firstCount: NameCount = NameCount(),
        val otherCount: NameCount = NameCount()
) {
    fun getTotalCount(): Int =
            maleFirstCount
                    .plus(maleOtherCount)
                    .plus(maleAllCount)
                    .plus(femaleFirstCount)
                    .plus(femaleOtherCount)
                    .plus(femaleAllCount)

}

data class NameCount(
        val female: Int = 0,
        val male: Int = 0,
        val total: Int = 0
)
