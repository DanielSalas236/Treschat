package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import lombok.Getter
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Getter
@Entity
@Table(name = "tb_images_content")
@ApiModel(value = "Image - Model", description = "Entidad para administrar la información del contenido de las images")
class ImageContent : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imgc_id")
    var imgc_id: Long = 0

    @NotBlank(message = "El campo img_name no debe ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imageimg_id")
    var image: Image? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contentcont_id")
    @JsonIgnore
    var img_content: Content? = null
}