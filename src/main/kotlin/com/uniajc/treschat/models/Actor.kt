package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import lombok.Getter
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Getter
@Entity
@Table(name = "tb_actor")
@ApiModel(value = "Actor - Model", description = "Entidad para administrar la información de los actores")
class Actor : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_id")
    var act_id: Long = 0

    @NotBlank(message = "El campo act_name no debe ser nulo")
    @Column(name = "act_name")
    var act_name: String = ""

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "actor", fetch = FetchType.EAGER)
    @JsonIgnore
    var actorContents: List<ActorContent>? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}