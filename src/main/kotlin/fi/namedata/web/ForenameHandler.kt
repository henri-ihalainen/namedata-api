package fi.namedata.web

import fi.namedata.service.NameService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body

@Component
class ForenameHandler(val service: NameService) {
    fun getForenames(req: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON)
            .body(service.getForenames(req.queryParams()["sortBy"]?.first()))
}