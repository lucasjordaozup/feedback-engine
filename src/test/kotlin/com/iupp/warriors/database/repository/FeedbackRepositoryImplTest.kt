package com.iupp.warriors.database.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.iupp.warriors.database.entity.FeedbackEntity
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.mockk
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@MicronautTest
internal class FeedbackRepositoryImplTest: AnnotationSpec(){

    val cqlSession = mockk<CqlSession>(relaxed = true)
    val feedbackRepositoryImpl = FeedbackRepositoryImpl(cqlSession)
    lateinit var feedbackEntity: FeedbackEntity

    @BeforeEach
    fun setUp(){
        feedbackEntity = FeedbackEntity(
            "Descricao teste",
            "Titulo teste",
            UUID.randomUUID(),
            LocalDateTime.now()
        )
    }

    @Test
    fun `Deve retornar um optional null`(){
        val testResult = feedbackRepositoryImpl.findById(feedbackEntity.id!!)

        testResult.isPresent shouldBe false
    }

    @Test
    fun `deve retornar sucesso quando for feito um buscar lista`(){
        val result = cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "SELECT * FROM feedbackkeyspace.feedbacks LIMIT 10000"
                )
        ).map { feedback ->
            FeedbackEntity(
                feedback.getString("descricao"),
                feedback.getString("titulo"),
                feedback.getUuid("id"),
                LocalDateTime.ofInstant(feedback.getInstant("createdAt"), ZoneOffset.UTC)
        ) }.toList()

        val testResult = feedbackRepositoryImpl.findAll()
        testResult shouldBe result
    }
}
