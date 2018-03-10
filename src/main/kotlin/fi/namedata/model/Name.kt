package fi.namedata.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum class NameType {
    MEN_FIRST, MEN_OTHER, MEN_ALL, WOMEN_FIRST, WOMEN_OTHER, WOMEN_ALL, SURNAME
}

@Entity
data class Name(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val name: String,
        val count: Int,
        val type: NameType
)
