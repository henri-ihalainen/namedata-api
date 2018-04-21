package fi.namedata

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig: WebFluxConfigurer {

    @Value("\${app.allowedOrigins}")
    lateinit var allowedOrigins: Array<String>

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins(*allowedOrigins)
                .allowedMethods("GET")
                .allowedHeaders("*")
    }
}