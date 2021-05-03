package com.uniajc.treschat.services.impl

import com.uniajc.treschat.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import com.uniajc.treschat.models.User
import com.uniajc.treschat.models.dto.UserDTO
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*


@Service
class UserService @Autowired constructor(userRepository: UserRepository) : UserDetailsService {
    private val userRepository: UserRepository

    @Autowired
    private val bcryptEncoder: PasswordEncoder? = null
    fun update(user: User): User {
        return userRepository.save(user)
    }

    fun getAllUsers(): List<User>{
        return userRepository.findAll()
    }

    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User? = userRepository.findByEmail(email)
        if(user == null)
            throw UsernameNotFoundException("Usuario no encontrado con el correo: $email")
        return org.springframework.security.core.userdetails.User(user.email, user.password, ArrayList<GrantedAuthority>())
    }

    fun save(user: UserDTO): User? {
        return if (userRepository.findByEmail(user.email) == null) {
            var newUser = User()
            newUser.email = user.email
            try {
                newUser.password = bcryptEncoder!!.encode(user.password)
            } catch (e: Exception) {
                System.err.println(e)
            }
            newUser.name = user.name
            userRepository.save(newUser)
        } else {
            return null
        }
    }

    fun deleteById(id: Long): Any? {
        userRepository.deleteById(id)
        return null
    }

    init {
        this.userRepository = userRepository
    }
}