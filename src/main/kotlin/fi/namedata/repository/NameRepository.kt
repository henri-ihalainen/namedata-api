package fi.namedata.repository

import fi.namedata.model.FirstName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class NameRepository(@Autowired val template: ReactiveMongoTemplate) {

    fun findAll(sortTerm: String) = template.find<FirstName>(Query().with(Sort.by(Sort.Direction.DESC, sortTerm)))

    fun findAll() = template.find<FirstName>(Query().with(Sort.by("name")))

    fun saveAll(entities: Collection<FirstName>) = template.insertAll(entities)
}