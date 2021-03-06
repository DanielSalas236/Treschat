package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tb_directors_content")
@ApiModel(
    value = "DirectorContent - Model",
    description = "Entidad para administrar la información del contenido del director"
)
class DirectorContent : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dirc_id")
    @JvmField
    var dirc_id: Long = 0

    @NotBlank(message = "El campo actoract_id no debe ser nulo")
    @ManyToOne
    @JoinColumn(name = "directordir_id")
    @JvmField
    var director: Director? = null

    @NotBlank(message = "El campo contentcont_id no debe ser nulo")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contentcont_id")
    @JvmField
    var dir_content: Content? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}