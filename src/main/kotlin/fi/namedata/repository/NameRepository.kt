package fi.namedata.repository

import fi.namedata.model.Forename
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class NameRepository(@Autowired val template: ReactiveMongoTemplate) {

    fun find(query: Query): Flux<Forename> = template.find(query)

    fun findAll(sortTerm: String) = template.find<Forename>(Query().with(Sort.by(Sort.Direction.DESC, sortTerm)))

    fun findAll() = template.find<Forename>(Query().with(Sort.by("name")))

    fun saveAll(entities: Collection<Forename>) = template.insertAll(entities)
}