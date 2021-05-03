package com.uniajc.treschat.services

import com.uniajc.treschat.models.User
import com.uniajc.treschat.models.dto.UserDTO
import com.uniajc.treschat.repositories.UserRepository
import com.uniajc.treschat.services.impl.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*


@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @MockBean
    private val userRepository = Mockito.mock(UserRepository::class.java)

    @InjectMocks
    private val userService: UserService? = null

    @Test
    @DisplayName("Test register user")
    fun should_register_user_successfully() {
        // Configuracion del mock repository
        val newUser = User()
        newUser.name = "Pepito Perez";
        newUser.email = "pepitop@gmail.com"
        newUser.password = "pepito123"
        val user = UserDTO()
        user.name = "Pepito Perez"
        user.email = "pepitop@gmail.com"
        user.password = "pepito123"
        doReturn(newUser).`when`<UserRepository>(userRepository).save<User>(ArgumentMatchers.any<User>())

        // Ejecución de la llamada al servicio
        val returnedUser: User? = userService!!.save(user)

        // Asegurarse de la respuesta
        Assertions.assertNotNull(returnedUser, "Los campos del usuario no deben ser nulos")
    }

    @Test
    @DisplayName("Test update user")
    fun should_update_user_successfully() {
        val user = User()
        user.name = "Daniel"
        user.email = "cdaniel@hotmail.com"
        user.password = "daniel"
        doReturn(user).`when`<UserRepository>(userRepository).save<User>(ArgumentMatchers.any<User>())

        // Ejecución de la llamada al servicio
        val returnedUser: User = userService!!.update(user)

        // Asegurarse de la respuesta
        Assertions.assertNotNull(returnedUser, "Los campos del usuario no deben ser nulos")
    }

    @Test
    @DisplayName("Test find user by id")
    fun should_return_user_by_id() {
        // Configuracion del mock repository
        val user = User()
        user.id = -1L
        user.name = "Daniel"
        user.email = "cdaniel@hotmail.com"
        user.password = "daniel123"
        doReturn(Optional.of<Any>(user)).`when`(userRepository).findById(-1L)

        // Ejecución de la llamada al servicio
        val returnedUser: Optional<User> = userService!!.findById(-1L)

        // Asegurarse de la respuesta
        Assertions.assertTrue(returnedUser.isPresent, "Usuario no encontrado")
        Assertions.assertSame(returnedUser.get(), user, "El usuario encontrado no es el mismo que el del mock")
    }

    @Test
    @DisplayName("Test load user by username")
    fun should_return_user_by_username() {
        // Configuracion del mock repository
        val newUser = User()
        newUser.name = "Pepito Perez"
        newUser.email = "pepitop@gmail.com"
        newUser.password = "pepito123"
        doReturn(newUser).`when`<UserRepository>(userRepository).findByEmail(newUser.email)

        // Ejecución de la llamada al servicio
        val returnedUser = userService!!.loadUserByUsername(newUser.email)

        // Asegurarse de la respuesta
        Assertions.assertNotNull(returnedUser, "No se encontró el usuario")
    }

    @Test
    @DisplayName("Test load user by username - fail")
    fun should_not_return_user_by_username() {
        val thrown = Assertions.assertThrows(
            UsernameNotFoundException::class.java,
            { userService!!.loadUserByUsername("fail@gmail.com") }, "No arrojó la excepción"
        )
        // Asegurarse de la respuesta
        Assertions.assertNotNull(thrown)
    }

    @Test
    @DisplayName("Test delete user")
    fun should_delete_user_successfully() {
        val user = User()
        user.id = -2L
        user.name = "Daniel"
        user.email = "cdaniel@hotmail.com"
        user.password = "daniel"
        doReturn(Optional.of<Any>(user)).`when`(userRepository).findById(-1L)

        // Ejecución de la llamada al servicio
        userService!!.deleteById(user.id)

        // Asegurarse de la respuesta
        Mockito.verify(userRepository).deleteById(user.id)
    }

    @Test
    @DisplayName("Test findAllUsers")
    fun should_get_all_user() {
        val user: List<User> = userService!!.getAllUsers()
        Assertions.assertNotNull(user)
    }
}