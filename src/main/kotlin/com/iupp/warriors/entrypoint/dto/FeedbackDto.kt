package com.iupp.warriors.entrypoint.dto

import java.util.*

data class FeedbackDto (
    val id: UUID,
    val titulo: String,
    val descricao: String
)