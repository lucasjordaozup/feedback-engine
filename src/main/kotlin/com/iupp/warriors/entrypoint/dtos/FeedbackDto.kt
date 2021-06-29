package com.iupp.warriors.entrypoint.dtos

import java.util.*

data class FeedbackDto (
    val id: UUID,
    val titulo: String,
    val descricao: String
)