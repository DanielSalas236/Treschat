package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tb_director")
@ApiModel(
    value = "Director - Model",
    description = "Entidad para administrar la información del contenido del director"
)
class Director : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dir_id")
    @JvmField
    var dir_id: Long = 0

    @NotBlank(message = "El campo dir_name no debe ser nulo")
    @Column(name = "dir_name")
    @JvmField
    var dir_name: String = ""

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "director", fetch = FetchType.EAGER)
    @JsonIgnore
    @JvmField
    var directorContents: List<DirectorContent>? = null
}