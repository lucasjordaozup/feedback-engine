package com.iupp.warriors.entrypoint.controller

import com.iupp.warriors.core.mapper.ConverterFeedback
import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.core.ports.FeedbackServicePort
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
import java.time.LocalDateTime
import java.util.*

@MicronautTest
@ExtendWith(MockKExtension::class)
internal class FeedbackControllerTest: AnnotationSpec(){

    @MockK
    lateinit var service: FeedbackServicePort

    @InjectMockKs
    lateinit var controller: FeedbackController

    lateinit var feedback: Feedback

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
        feedback = Feedback(
            "descricao teste",
            "titulo teste",
            UUID.randomUUID(),
            LocalDateTime.now()
        )
    }

    @Test
    fun `Deve retornar um feedbackdto por id`(){

        every { service.findById(feedback.id!!) } answers {
            Optional.of(feedback)
        }

        val response = controller.consultar(feedback.id!!)

        response.status shouldBe HttpStatus.OK
        response.body() shouldBe ConverterFeedback.feedbackToFeedbackDto(feedback)
    }

    @Test
    fun `Deve retornar uma lista de feedbackdto`(){
        every { service.findAll() } answers {
            listOf(feedback, feedback)
        }

        val response = controller.listar()

        response.status shouldBe HttpStatus.OK
        response.body() shouldNotBe listOf(null)
    }
}