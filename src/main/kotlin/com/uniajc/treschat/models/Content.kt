package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import io.swagger.annotations.ApiModel
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tb_content")
@ApiModel(
    value = "Content - Model",
    description = "Entidad para administrar la información del contenido de las peliculas"
)
class Content : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cont_id")
    @JvmField
    var cont_id: Long = 0

    @NotBlank(message = "El campo cont_name no debe ser nulo")
    @Column(name = "cont_name")
    @JvmField
    var cont_name: String = ""

    @Column(name = "cont_review")
    @JvmField
    var cont_review = 0

    @ManyToOne
    @JoinColumn(name = "content_typect_id")
    @JsonManagedReference
    @JvmField
    var cont_type: ContentType? = null

    @OneToOne
    @JoinColumn(name = "previewprev_id")
    @JvmField
    var preview: Preview? = null

    @OneToMany(mappedBy = "dir_content")
    @JvmField
    var directors_content: List<DirectorContent>? = null

    @OneToMany(mappedBy = "img_content")
    @JvmField
    var images_content: List<ImageContent>? = null

    @OneToMany(mappedBy = "act_content")
    @JvmField
    var actors_content: List<ActorContent>? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}