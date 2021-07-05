package com.iupp.warriors.entrypoint.controller

import com.iupp.warriors.core.ports.FeedbackRepositoryPort
import com.iupp.warriors.core.mapper.ConverterFeedback
import com.iupp.warriors.core.ports.FeedbackServicePort
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import java.lang.IllegalArgumentException
import java.util.*

@Controller("/feedbacks")
class FeedbackController(private val service: FeedbackServicePort) {

    @Get("/{id}")
    fun consultar(@PathVariable id: UUID): HttpResponse<Any>{
        val response = service.findById(id).get()
        return HttpResponse.ok(ConverterFeedback.feedbackToFeedbackDto(response))
    }

    @Get("/listar")
    fun listar(): HttpResponse<Any>{
        return HttpResponse.ok(ConverterFeedback.feedbackListToFeedbackDtoList(service.findAll()))
    }

}