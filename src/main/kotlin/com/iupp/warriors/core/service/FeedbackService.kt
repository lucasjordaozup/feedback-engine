package com.iupp.warriors.core.service

import com.iupp.warriors.core.mapper.ConverterFeedback
import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.core.ports.FeedbackRepositoryPort
import com.iupp.warriors.core.ports.FeedbackServicePort
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackService(private val repository: FeedbackRepositoryPort): FeedbackServicePort {
    override fun findById(id: UUID): Optional<Feedback> {
        val feedbackEntity = repository.findById(id).orElseThrow{
            IllegalArgumentException("Feedback não encontrado pelo id informado.") }

        return Optional.of(ConverterFeedback.feedbackEntityToFeedback(feedbackEntity))
    }

    override fun findAll(): List<Feedback> {
        val listFeedbackEntity = repository.findAll()
        return listFeedbackEntity.map {
            ConverterFeedback.feedbackEntityToFeedback(it!!)
        }
    }

}