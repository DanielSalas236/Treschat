package com.uniajc.treschat.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import lombok.Getter
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Getter
@Entity
@Table(name = "tb_preview")
@ApiModel(value = "Preview - Model", description = "Entidad para administrar la información del contenido del preview")
class Preview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prev_id")
    var prev_id: Long = 0

    @NotBlank(message = "El campo prev_path no debe ser nulo")
    @Column(name = "prev_path")
    var prev_path: String? = null

    @OneToOne(mappedBy = "preview", fetch = FetchType.LAZY)
    @JsonIgnore
    var prev_content: Content? = null
}