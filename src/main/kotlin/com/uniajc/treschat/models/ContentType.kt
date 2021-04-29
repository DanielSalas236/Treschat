package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonBackReference
import io.swagger.annotations.ApiModel
import lombok.Getter
import lombok.ToString
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Getter
@Entity
@Table(name = "tb_content_type")
@ApiModel(
    value = "ContentType - Model",
    description = "Entidad para administrar la información del tipo de contenido; series, peliculas, etc..."
)
class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_id")
    var ct_id: Long = 0

    @NotBlank(message = "El campo ct_name no debe ser nulo")
    @Column(name = "ct_name")
    var ct_name: String = ""

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "cont_type", fetch = FetchType.EAGER)
    @JsonBackReference
    @ToString.Exclude
    var content: List<Content>? = null
}