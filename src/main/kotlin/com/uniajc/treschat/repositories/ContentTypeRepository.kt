package com.uniajc.treschat.repositories

import com.uniajc.treschat.models.ContentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ContentTypeRepository : JpaRepository<ContentType, Long>