package com.uniajc.treschat.controllers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@ActiveProfiles("test")
@ExtendWith(
    MockitoExtension::class
)
class ContentControllerTest {
    @Autowired
    protected var mvc: MockMvc? = null

    @Test
    @DisplayName("Test find all contents")
    @Order(1)
    @Transactional(propagation = Propagation.REQUIRED)
    @WithMockUser
    fun findAllContents() {
        mvc!!.perform(MockMvcRequestBuilders.get(ContentController.URL_CONTROLLER + "/findContentAll"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find all content types")
    @Order(2)
    @Transactional(propagation = Propagation.REQUIRED)
    @WithMockUser
    fun findAllContentTypes() {
        mvc!!.perform(MockMvcRequestBuilders.get(ContentController.URL_CONTROLLER + "/findContentTypeAll"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find content by title")
    @Order(3)
    @Transactional(propagation = Propagation.REQUIRED)
    @WithMockUser
    fun findAllContentByTitle() {
        mvc!!.perform(
            MockMvcRequestBuilders.get(ContentController.URL_CONTROLLER + "/findContentByTitle").content("god")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("Test find content by title")
    @Order(4)
    @Transactional(propagation = Propagation.REQUIRED)
    fun findAllContentByTitleFail() {
        mvc!!.perform(
            MockMvcRequestBuilders.get(ContentController.URL_CONTROLLER + "/findContentByTitle").content("god")
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}