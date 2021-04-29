package com.uniajc.treschat.repositories

import com.uniajc.treschat.models.Content
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface ContentRepository : JpaRepository<Content, Long> {
    @Query(value = "SELECT * FROM tb_content WHERE cont_name iLIKE %:title%", nativeQuery = true)
    fun findContentByTitle(@Param("title") title: String?): List<Content>
}