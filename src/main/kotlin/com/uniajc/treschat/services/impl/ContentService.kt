package com.uniajc.treschat.services.impl

import com.uniajc.treschat.models.Content
import com.uniajc.treschat.models.ContentType
import com.uniajc.treschat.repositories.ContentRepository
import com.uniajc.treschat.repositories.ContentTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ContentService @Autowired internal constructor(
    contentRepository: ContentRepository,
    contentTypeRepository: ContentTypeRepository
) {
    private val contentRepository: ContentRepository
    private val contentTypeRepository: ContentTypeRepository

    fun getAllContents(): List<Content> {
        return contentRepository.findAll()
    }

    fun getContentByTitle(title: String?): List<Content> {
        return contentRepository.findContentByTitle(title)
    }

    fun getAllContentTypes(): List<ContentType> {
        return contentTypeRepository.findAll()
    }

    init {
        this.contentRepository = contentRepository
        this.contentTypeRepository = contentTypeRepository
    }
}