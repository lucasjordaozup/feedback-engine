package com.iupp.warriors.core.mapper

import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.entrypoint.dtos.FeedbackDto
import java.util.*

class ConverterFeedback {
    companion object{
        fun feedbackEntityToFeedbackDto(feedback: FeedbackEntity): FeedbackDto = FeedbackDto(
            feedback.id!!,
            feedback.titulo!!,
            feedback.descricao!!
        )

        fun feedbackEntityListToFeedbackDtoList(listEntity: List<FeedbackEntity?>): List<FeedbackDto> {
            return listEntity.map { entity -> FeedbackDto(entity?.id!!, entity.titulo!!, entity.descricao!!) }
        }

        fun feedbackEntityToFeedback(feedback: FeedbackEntity): Feedback {
            return Feedback(feedback.descricao, feedback.titulo, feedback.id, feedback.createdAt)
        }

        fun feedbackToFeedbackDto(feedback: Feedback): FeedbackDto = FeedbackDto(
            feedback.id!!,
            feedback.titulo!!,
            feedback.descricao!!
        )

        fun feedbackListToFeedbackDtoList(list: List<Feedback>): List<FeedbackDto> {
            return list.map {
                FeedbackDto(
                    it.id!!,
                    it.titulo!!,
                    it.descricao!!
                )
            }
        }

    }
}