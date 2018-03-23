package fi.namedata.web

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class Routes(val handlers: Handlers) {
    @Bean
    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/first-names", handlers::getFirstNames)
        }
    }
}