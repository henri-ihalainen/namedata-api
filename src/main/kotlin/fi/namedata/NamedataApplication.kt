package fi.namedata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class NamedataApplication

fun main(args: Array<String>) {
    runApplication<NamedataApplication>(*args)
}
