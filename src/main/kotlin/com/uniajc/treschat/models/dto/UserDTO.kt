package com.uniajc.treschat.models.dto

import lombok.Getter
import lombok.Setter


@Setter
@Getter
class UserDTO {
    val email: String = ""
    val password: String = ""
    val name: String = ""
}