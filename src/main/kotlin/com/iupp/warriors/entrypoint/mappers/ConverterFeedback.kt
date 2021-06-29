package com.iupp.warriors.entrypoint.mappers

import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.entrypoint.dtos.FeedbackDto

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
    }
}