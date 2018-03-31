package fi.namedata.repository

import fi.namedata.model.NewForename
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

    fun find(query: Query): Flux<NewForename> = template.find(query)

    fun findAll(sortTerm: String, direction: Sort.Direction = Sort.Direction.ASC) =
            template.find<NewForename>(Query().with(Sort.by(direction, sortTerm)))

    fun findAll() = findAll("count", Sort.Direction.DESC)

    fun deleteAll() = template.remove<NewForename>(Query())
}