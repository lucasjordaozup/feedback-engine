package com.iupp.warriors.entrypoint.controllers

import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.database.repository.FeedbackRepository
import com.iupp.warriors.entrypoint.mappers.ConverterFeedback
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

@MicronautTest
@ExtendWith(MockKExtension::class)
internal class FeedbackControllerTest: AnnotationSpec(){

    @MockK
    lateinit var repository: FeedbackRepository

    @InjectMockKs
    lateinit var controller: FeedbackController

    lateinit var feedback: FeedbackEntity

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
        feedback = FeedbackEntity(
            "descricao teste",
            "titulo teste",
            UUID.randomUUID(),
            LocalDateTime.now()
        )
    }

    @Test
    fun `Deve retornar um feedbackdto por id`(){

        every { repository.findById(feedback.id!!) } answers {
            Optional.of(feedback)
        }

        val response = controller.consultar(feedback.id!!)

        response.status shouldBe HttpStatus.OK
        response.body() shouldBe ConverterFeedback.feedbackEntityToFeedbackDto(feedback)
    }

    @Test
    fun `Deve retornar uma excecao quando o id informado nao for de um feedback existente`(){

        every { repository.findById(feedback.id!!) } answers {
            Optional.empty()
        }

        val response = shouldThrow<IllegalArgumentException> {
            controller.consultar(feedback.id!!)
        }

        response.message shouldBe "Nenhum resultado encontrado com o id informado"
    }

    @Test
    fun `Deve retornar uma lista de feedbackdto`(){
        every { repository.findAll() } answers {
            listOf(feedback, feedback)
        }

        val response = controller.listar()

        response.status shouldBe HttpStatus.OK
        response.body() shouldNotBe listOf(null)
    }
}