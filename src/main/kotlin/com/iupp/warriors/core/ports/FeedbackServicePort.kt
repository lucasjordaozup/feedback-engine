package com.iupp.warriors.core.ports

import com.iupp.warriors.core.model.Feedback
import java.util.*

interface FeedbackServicePort {
    fun findById(id: UUID): Optional<Feedback>
    fun findAll(): List<Feedback>
}