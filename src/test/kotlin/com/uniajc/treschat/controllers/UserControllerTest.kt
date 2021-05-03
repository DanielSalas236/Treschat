package com.uniajc.treschat.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.uniajc.treschat.config.security.JwtTokenUtil
import com.uniajc.treschat.models.User
import com.uniajc.treschat.models.dto.UserDTO
import com.uniajc.treschat.services.impl.UserService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@ActiveProfiles("test")
@ExtendWith(
    MockitoExtension::class
)
class UserControllerTest @Autowired constructor(userService: UserService) {
    private val userService: UserService

    @Autowired
    protected var mvc: MockMvc? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    protected fun mapToJson(obj: Any?): String {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        return objectMapper.writeValueAsString(obj)
    }

    @Test
    @DisplayName("Test register user controller")
    @Order(1)
    @Transactional(propagation = Propagation.REQUIRED)
    fun create_user_controller() {
        val user = UserDTO()
        user.name = name
        user.email = email
        user.password = password
        mvc!!.perform(
            MockMvcRequestBuilders.post(AuthController.URL_CONTROLLER + "/register")
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(user))
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test authenticate user controller")
    @Order(2)
    @Transactional(propagation = Propagation.REQUIRED)
    fun authenticate_user_controller() {
        val credentials = "{\"password\": \"123\", \"username\": \"prueba@gmail.com\"}"
        mvc!!.perform(
            MockMvcRequestBuilders.post(AuthController.URL_CONTROLLER + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON).content(credentials)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test create user controller")
    @Order(3)
    @Transactional(propagation = Propagation.REQUIRED)
    @WithMockUser
    fun update_user_controller() {
        val user = User()
        user.id = id
        user.name = name
        user.email = email
        user.password = password
        mvc!!.perform(
            MockMvcRequestBuilders.put(AuthController.URL_CONTROLLER + "/update")
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(user))
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find all users controller")
    @Order(4)
    @Transactional(propagation = Propagation.REQUIRED)
    @WithMockUser
    fun findAll() {
        mvc!!.perform(MockMvcRequestBuilders.get(AuthController.URL_CONTROLLER + "/findUserAll"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find all users controller - with header")
    @Order(5)
    @Transactional(propagation = Propagation.REQUIRED)
    fun findAll_header() {
        val userDetails: UserDetails = userService
            .loadUserByUsername("daniiel@gmail.com")
        val token: String = jwtTokenUtil!!.generateToken(userDetails)
        mvc!!.perform(
            MockMvcRequestBuilders.get(AuthController.URL_CONTROLLER + "/findUserAll").header(
                "Authorization",
                "Bearer $token"
            )
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find user by id controller")
    @Order(6)
    @WithMockUser
    fun findByIdTest() {
        mvc!!.perform(MockMvcRequestBuilders.get(AuthController.URL_CONTROLLER + "/findById/" + id))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test delete user by id controller")
    @Transactional
    @Order(7)
    @WithMockUser
    fun deleteByIdTest() {
        mvc!!.perform(MockMvcRequestBuilders.delete(AuthController.URL_CONTROLLER + "/deleteById/" + id))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test register user controller - fail")
    @Order(8)
    @Transactional(propagation = Propagation.REQUIRED)
    fun create_user_controller_error() {
        val user = UserDTO()
        user.name = name
        user.email = "prueba@gmail.com"
        user.password = password
        mvc!!.perform(
            MockMvcRequestBuilders.post(AuthController.URL_CONTROLLER + "/register")
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(user))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    companion object {
        private var id = -1L
        private var name = "Pepito"
        private var email = "prueba@hotmail.com"
        private var password = "hoy"
    }

    init {
        this.userService = userService
    }
}