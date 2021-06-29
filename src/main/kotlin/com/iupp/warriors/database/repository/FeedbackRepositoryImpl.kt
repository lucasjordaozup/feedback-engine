package com.iupp.warriors.database.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.iupp.warriors.database.entity.FeedbackEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackRepositoryImpl(private val cqlSession: CqlSession): FeedbackRepository {
    val LOG:Logger = LoggerFactory.getLogger(FeedbackRepositoryImpl::class.java)
    override fun findById(id: UUID): Optional<FeedbackEntity> {
        try {
            val result = cqlSession.execute(
                SimpleStatement
                    .newInstance(
                        "SELECT * FROM feedbackkeyspace.feedbacks WHERE id = ? LIMIT 1",
                        id
                    )
            )

            LOG.info("Consulta ao banco de dados concluida com sucesso {}", "findById")
            val entityResult = result.map { feedback ->
                    FeedbackEntity(
                        feedback.getString("descricao")!!,
                        feedback.getString("titulo")!!,
                        feedback.getUuid("id")!!,
                        LocalDateTime.ofInstant(feedback.getInstant("createdAt"), ZoneOffset.UTC)
                    ) }.firstOrNull()
            if(entityResult != null){
                return Optional.of(entityResult)
            }
            return Optional.empty()
        }catch (error: IllegalStateException){
            LOG.error("Ocorreu um erro ao tentar consultar o banco.")
            return Optional.empty<FeedbackEntity>()
        }
    }

    override fun findAll(): List<FeedbackEntity?> {
        try {
            val result = cqlSession.execute(
                SimpleStatement
                    .newInstance(
                        "SELECT * FROM feedbackkeyspace.feedbacks LIMIT 10000"
                    )
            )

            LOG.info("Consulta ao banco de dados concluida com sucesso {}", "findAll")
            return result
                .map { feedback ->
                    FeedbackEntity(
                        feedback.getString("descricao")!!,
                        feedback.getString("titulo")!!,
                        feedback.getUuid("id")!!,
                        LocalDateTime.ofInstant(feedback.getInstant("createdAt"), ZoneOffset.UTC)
                    ) }.toList()
        }catch (error: IllegalStateException){
            LOG.error("Ocorreu um erro ao tentar consultar o banco.")
            return listOf(null)
        }
    }
}