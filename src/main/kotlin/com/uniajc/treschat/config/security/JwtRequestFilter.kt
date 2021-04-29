package com.uniajc.treschat.config.security

import com.uniajc.treschat.services.impl.UserService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        // El JWT tiene el formato "Bearer token"
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil?.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println("JWT Token has expired")
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String")
        }

        // Una vez tenemos el token, lo validamos
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService!!.loadUserByUsername(username)

            // si el token es válido, configura Spring Security para manualmente
            // establecer la autenticación
            if (jwtTokenUtil != null) {
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    // Después de configurar la autenticacion, especificamos que el usuario actual está autenticado
                    // así que el pasó las Spring Security Configurations satisfactoriamente
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }
        chain.doFilter(request, response)
    }
}