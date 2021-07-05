package com.iupp.warriors.core.ports

import com.iupp.warriors.database.entity.FeedbackEntity
import java.util.*

interface FeedbackRepositoryPort {
    fun findById(id: UUID): Optional<FeedbackEntity>
    fun findAll(): List<FeedbackEntity?>
}