package fi.namedata.repository

import fi.namedata.model.FirstName
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface NameRepository: ReactiveCrudRepository<FirstName, ObjectId> {
    fun findByMaleAllCountBetween(countGT: Int, countLT: Int): Flux<FirstName>

    @Query(value = "{ \$or: [ { maleAllCount: { \$gt: 0 } }, { femaleAllCount: { \$gt: 0 } } ] }")
    fun findNamesWithAllCountGreaterThanZero(): Flux<FirstName>
}