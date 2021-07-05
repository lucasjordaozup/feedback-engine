package com.iupp.warriors.core.model

import java.time.LocalDateTime
import java.util.*

class Feedback (
    var descricao: String? = null,
    var titulo: String? = null,
    var id: UUID? = null,
    var createdAt: LocalDateTime? = null
)