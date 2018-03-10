package fi.namedata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NamedataApplication

fun main(args: Array<String>) {
    runApplication<NamedataApplication>(*args)
}
