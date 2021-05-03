package com.uniajc.treschat.models

import com.uniajc.treschat.models.jwt.JwtRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class JwtRequestTest {
    @Test
    @DisplayName("Test fill request data")
    fun should_fill_request_data() {
        val jwtRequest = JwtRequest("prueba", "prueba123")
        Assertions.assertNotNull(jwtRequest, "No se llenó el JwtRequest")
    }
}