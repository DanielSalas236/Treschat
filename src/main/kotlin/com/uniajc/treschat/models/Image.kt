package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tb_image")
@ApiModel(value = "Image - Model", description = "Entidad para administrar la información de las images")
class Image : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    @JvmField
    var img_id: Long = 0

    @NotBlank(message = "El campo img_name no debe ser nulo")
    @Column(name = "img_name")
    @JvmField
    var img_name: String? = null

    @NotBlank(message = "El campo img_path no debe ser nulo")
    @Column(name = "img_path")
    @JvmField
    var img_path: String? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "image", fetch = FetchType.EAGER)
    @JsonIgnore
    @JvmField
    var imageContentList: List<ImageContent>? = null
}