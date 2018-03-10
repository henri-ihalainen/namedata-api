package fi.namedata.web

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Component
class Routes {
    @Bean
    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/first-names", {ok().body(Mono.just("it works!"), String::class.java)})
        }
    }
}