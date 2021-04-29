package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import lombok.Getter
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Getter
@Entity
@Table(name = "tb_actors_content")
@ApiModel(
    value = "ActorContent - Model",
    description = "Entidad para administrar la información del contenido del actor "
)
class ActorContent : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actc_id")
    var actc_id: Long = 0

    @NotBlank(message = "El campo actoract_id no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actoract_id")
    var actor: Actor? = null

    @NotBlank(message = "El campo contentcont_id no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contentcont_id")
    @JsonIgnore
    var act_content: Content? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}