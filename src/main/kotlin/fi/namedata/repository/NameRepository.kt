package fi.namedata.repository

import fi.namedata.model.Forename
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class NameRepository(@Autowired val template: ReactiveMongoTemplate) {

    fun find(query: Query): Flux<Forename> = template.find(query)

    fun findAll(sortTerm: String, direction: Sort.Direction) = template.find<Forename>(Query().with(Sort.by(direction, sortTerm)))

    fun findAll() = template.find<Forename>(Query())

    fun saveAll(entities: Collection<Forename>): Flux<Forename> = template.insertAll(entities)

    fun deleteAll() = template.remove<Forename>(Query())
}