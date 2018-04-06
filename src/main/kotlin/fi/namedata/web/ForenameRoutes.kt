package fi.namedata.web

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ForenameRoutes(val handlers: ForenameHandler) {
    @Bean
    fun router() = router {
        "/forenames".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", handlers::getForenames)
            }
        }
    }
}