package com.uniajc.treschat.models.dto

import lombok.AllArgsConstructor
import lombok.Builder

@AllArgsConstructor
@Builder
class SimpleObjectResponse(
    val code: Int = 0,
    val message: String = "",
    val value: Any = ""
) {
    data class Builder(
        var code: Int = 0,
        var message: String = "",
        var value: Any = ""
    ) {
        fun code(code: Int) = apply { this.code = code }
        fun message(message: String) = apply { this.message = message }
        fun value(value: Any) = apply { this.value = value }

        fun build() = SimpleObjectResponse(code, message, value)
    }
}