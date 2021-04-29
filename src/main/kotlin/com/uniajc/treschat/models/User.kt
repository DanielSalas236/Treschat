package com.uniajc.treschat.models

import io.swagger.annotations.ApiModel
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tb_user")
@ApiModel(value = "Usuario - Model", description = "Entidad para administrar la información de los usuarios")
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    var id: Long = 0

    @Column(name = "us_name")
    @NotBlank(message = "El campo us_name no debe ser nulo")
    var name: String = ""

    @Column(name = "us_email")
    @NotBlank(message = "El campo us_email no debe ser nulo")
    var email: String = ""

    @Column(name = "us_password")
    @NotBlank(message = "El campo us_password no debe ser nulo")
    var password: String = ""

    companion object {
        private const val serialVersionUID = 1L
    }

}