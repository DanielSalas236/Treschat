package com.uniajc.treschat.config.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse,
        authException: AuthenticationException
    ) {

        //Esto se llama cuando un usuario intenta acceder a un REST asegurado sin proveer alguna credencial
        //Solo enviamos un error 401 de Unauthorized
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}