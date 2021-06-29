package com.iupp.warriors.database.repository

import com.iupp.warriors.database.entity.FeedbackEntity
import java.util.*

interface FeedbackRepository {
    fun findById(id: UUID): Optional<FeedbackEntity>
    fun findAll(): List<FeedbackEntity?>
}