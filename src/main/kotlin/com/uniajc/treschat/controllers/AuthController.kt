package com.uniajc.treschat.controllers

import com.uniajc.treschat.config.security.JwtTokenUtil
import com.uniajc.treschat.models.jwt.JwtRequest
import com.uniajc.treschat.models.jwt.JwtResponse
import com.uniajc.treschat.models.dto.SimpleObjectResponse
import com.uniajc.treschat.models.User
import com.uniajc.treschat.models.dto.UserDTO
import com.uniajc.treschat.services.impl.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import javax.validation.Valid
import javax.validation.constraints.NotNull


@RestController
@Api(tags = ["User Type API"])
@RequestMapping("/user")
@CrossOrigin
@Validated
class AuthController @Autowired constructor(userService: UserService) {
    private val userService: UserService
    var modelMapper = ModelMapper()

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    @Bean
    fun authModelMapper(): ModelMapper {
        return modelMapper
    }

    @ApiOperation(
        nickname = "Authenticate",
        notes = "Este método es para autenticar al usuario.",
        value = "Autenticar usuario",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Usuario autenticado con éxito",
            response = User::class
        ), ApiResponse(
            code = 400,
            message = "Credenciales incorrectas",
            response = User::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @PostMapping(value = ["/authenticate"], produces = ["application/json;charset=UTF-8"])
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<SimpleObjectResponse> {
        return try {
            authenticate(authenticationRequest.username, authenticationRequest.password)
            val userDetails: UserDetails = userService
                .loadUserByUsername(authenticationRequest.username)
            val token: String = jwtTokenUtil!!.generateToken(userDetails)
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Usuario autenticado con éxito")
                    .value(JwtResponse(token)).build(), HttpStatus.OK
            )
        } catch (e: Exception) {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value()).message("Credenciales incorrectas")
                    .value(e).build(), HttpStatus.BAD_REQUEST
            )
        }
    }

    @ApiOperation(
        nickname = "Registrar",
        notes = "Este método registra un usuario.",
        value = "Registrar usuario",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Usuario registrado con éxito",
            response = User::class
        ), ApiResponse(code = 400, message = "El usuario ya existe", response = User::class), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun saveUser(@RequestBody user: UserDTO): ResponseEntity<SimpleObjectResponse> {
        return try {
            val res = userService.save(user)
            if (res != null) {
                ResponseEntity<SimpleObjectResponse>(
                    SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Usuario registrado con éxito")
                        .value(user).build(),
                    HttpStatus.OK
                )
            } else {
                ResponseEntity<SimpleObjectResponse>(
                    SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value()).message("El usuario ya existe")
                        .value(user).build(),
                    HttpStatus.BAD_REQUEST
                )
            }
        } catch (e: Exception) {
            ResponseEntity<SimpleObjectResponse>(
                SimpleObjectResponse.Builder().code(HttpStatus.BAD_REQUEST.value()).message("Error al registrar")
                    .value(e).build(), HttpStatus.BAD_REQUEST
            )
        }
    }

    @ApiOperation(
        nickname = "update",
        notes = "Este método actualiza un usuario.",
        value = "Actualizar usuario",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Usuario actualizado con éxito",
            response = User::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @PutMapping(value = ["/update"], produces = ["application/json;charset=UTF-8"])
    fun update(@RequestBody user: @NotNull(message = "UserDto no puede ser nulo.") @Valid User?): ResponseEntity<SimpleObjectResponse> {
        userService.update(modelMapper.map(user, User::class.java))
        return ResponseEntity<SimpleObjectResponse>(
            SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Usuario actualizado con éxito").build(),
            HttpStatus.OK
        )
    }

    @ApiOperation(
        nickname = "findall",
        notes = "Este método lista todos los usuarios.",
        value = "Listar Usuarios",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Listados de usuarios con éxito",
            response = User::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @GetMapping(value = ["/findUserAll"], produces = ["application/json;charset=UTF-8"])
    fun findAll(): ResponseEntity<SimpleObjectResponse> {
        return ResponseEntity<SimpleObjectResponse>(
            SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Listados de usuarios con éxito")
                .value(userService.getAllUsers()).build(),
            HttpStatus.OK
        )
    }

    @ApiOperation(
        nickname = "findbyid",
        notes = "Este método lista al usuario mediante la búsqueda con su id.",
        value = "Buscar Usuario",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Encontrado con éxito",
            response = User::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @GetMapping(value = ["/findById/{id}"], produces = ["application/json;charset=UTF-8"])
    fun findById(@PathVariable id: Long): ResponseEntity<SimpleObjectResponse> {
        return ResponseEntity<SimpleObjectResponse>(
            SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Encontrado con éxito")
                .value(userService.findById(id)).build(),
            HttpStatus.OK
        )
    }

    @ApiOperation(
        nickname = "deletebyid",
        notes = "Este método elimina al usuario mediante su id.",
        value = "Eliminar Usuario",
        response = User::class,
        produces = "application/json"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Eliminado con éxito",
            response = User::class
        ), ApiResponse(
            code = 404,
            message = "No se encontró el recurso solicitado",
            response = User::class
        ), ApiResponse(code = 500, message = "Error con la conexión a la base de datos", response = User::class)]
    )
    @DeleteMapping(value = ["/deleteById/{id}"], produces = ["application/json;charset=UTF-8"])
    fun deleteById(@PathVariable id: Long): ResponseEntity<SimpleObjectResponse> {
        userService.deleteById(id)
        return ResponseEntity<SimpleObjectResponse>(
            SimpleObjectResponse.Builder().code(HttpStatus.OK.value()).message("Eliminado con éxito").value("")
                .build(),
            HttpStatus.OK
        )
    }

    fun authenticate(username: String, password: String) {
        try {
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }

    companion object {
        const val URL_CONTROLLER = "/user"
    }

    init {
        this.userService = userService
    }
}
