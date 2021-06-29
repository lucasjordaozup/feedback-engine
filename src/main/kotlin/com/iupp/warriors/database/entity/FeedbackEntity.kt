package com.iupp.warriors.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Introspected
data class FeedbackEntity(
    var descricao: String? = null,
    var titulo: String? = null,
    var id: UUID? = null,
    var createdAt: LocalDateTime? = null
)