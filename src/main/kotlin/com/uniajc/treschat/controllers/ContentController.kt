package com.uniajc.treschat.controllers

import com.uniajc.treschat.models.Content
import com.uniajc.treschat.models.ContentType
import com.uniajc.treschat.models.dto.SimpleObjectResponse
import com.uniajc.treschat.services.impl.ContentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.Exception

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.Filter
import java.util.*


@RestController
@Api(tags = ["Content Type API"])
@RequestMapping("/content")
@CrossOrigin
@Validated
class ContentController internal constructor(contentService: ContentService) {
    private val contentService: ContentService
    var modelMapper = ModelMapper()

    @Bean
    fun contentModelMapper(): ModelMapper {
        return modelMapper
    }

    @ApiOperation(
        nickname = "findall",
        notes = "Este método lista todos los contenidos.",
        value = "Listar Contenidos",
        response = Content::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Listados de contenido con éxito",
            response = Content::class
        ), ApiResponse(
            code = 400,
            message = "Posiblemente el token ha expirado",
            response = Content::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = Content::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = Content::class)]
    )
    @GetMapping(value = ["/findContentAll"], produces = ["application/json;charset=UTF-8"])
    fun findAll(): ResponseEntity<SimpleObjectResponse> {
        return try {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Listados de contenido con éxito")
                    .value(contentService.getAllContents()).build(),
                HttpStatus.OK
            )
        } catch (e: Exception) {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value())
                    .message("Posiblemente el token ha expirado").value(e).build(),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    @ApiOperation(
        nickname = "findcontentbytitle",
        notes = "Este método busca contenido a partir del titulo.",
        value = "Buscar contenido",
        response = Content::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Contenido encontrado con éxito",
            response = Content::class
        ), ApiResponse(
            code = 400,
            message = "Posiblemente el token ha expirado / contenido no encontrado",
            response = Content::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = Content::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = Content::class)]
    )
    @GetMapping(value = ["/findContentByTitle"], produces = ["application/json;charset=UTF-8"])
    fun findContentByTitle(@RequestBody title: String?): ResponseEntity<SimpleObjectResponse> {
        return try {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Contenido encontrado con éxito")
                    .value(contentService.getContentByTitle(title)).build(),
                HttpStatus.OK
            )
        } catch (e: Exception) {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value())
                    .message("Posiblemente el token ha expirado").value(e).build(),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    @ApiOperation(
        nickname = "findallcontenttypes",
        notes = "Este método lista todos los tipos de contenidos.",
        value = "Listar tipos de Contenidos",
        response = ContentType::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Listados de tipos de contenido con éxito",
            response = ContentType::class
        ), ApiResponse(
            code = 400,
            message = "Posiblemente el token ha expirado",
            response = ContentType::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = ContentType::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = ContentType::class)]
    )
    @GetMapping(value = ["/findContentTypeAll"], produces = ["application/json;charset=UTF-8"])
    fun findAllContentTypes(): ResponseEntity<SimpleObjectResponse> {
        return try {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.OK.value())
                    .message("Listados de tipos de contenido con éxito").value(contentService.getAllContentTypes())
                    .build(),
                HttpStatus.OK
            )
        } catch (e: Exception) {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value())
                    .message("Posiblemente el token ha expirado").value(e).build(),
                HttpStatus.BAD_REQUEST
            )
        }
    }

    companion object {
        const val URL_CONTROLLER = "/content"
    }

    init {
        this.contentService = contentService
    }

    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        // *** URL below needs to match the Vue client URL and port ***
        config.allowedOrigins = Collections.singletonList("http://vet.tecliz.com")
        config.allowedMethods = Collections.singletonList("*")
        config.allowedHeaders = Collections.singletonList("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE)
        return bean
    }

}