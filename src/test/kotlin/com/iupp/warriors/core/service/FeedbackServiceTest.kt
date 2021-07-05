package com.iupp.warriors.core.service

import com.iupp.warriors.core.ports.FeedbackRepositoryPort
import com.iupp.warriors.database.entity.FeedbackEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.util.*

@MicronautTest
@ExtendWith(MockKExtension::class)
internal class FeedbackServiceTest: AnnotationSpec(){

    @MockK
    lateinit var repository: FeedbackRepositoryPort

    @InjectMockKs
    lateinit var service: FeedbackService
    lateinit var feedbackEntity: FeedbackEntity

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
        feedbackEntity = FeedbackEntity(
            "Descricao teste",
            "Titulo teste",
            UUID.randomUUID(),
            LocalDateTime.now()
        )
    }

    @Test
    fun `Deve retornar uma lista de feedbacks`(){
        every { repository.findAll() } answers {
            listOf(feedbackEntity, feedbackEntity)
        }

        val response = service.findAll()
        response.size shouldBe 2
    }

    @Test
    fun `Deve retornar um feedback`(){
        every { repository.findById(feedbackEntity.id!!) } answers {
            Optional.of(feedbackEntity)
        }

        val response = service.findById(feedbackEntity.id!!)
        response.isPresent shouldBe true
    }

    @Test
    fun `Deve retornar uma excecao quando o id informado nao for de um feedback existente`(){

        every { repository.findById(feedbackEntity.id!!) } answers {
            Optional.empty()
        }

        val response = shouldThrow<IllegalArgumentException> {
            service.findById(feedbackEntity.id!!)
        }

        response.message shouldBe "Feedback não encontrado pelo id informado."
    }

}