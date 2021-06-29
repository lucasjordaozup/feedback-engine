package com.iupp.warriors.entrypoint.controllers

import com.iupp.warriors.database.repository.FeedbackRepository
import com.iupp.warriors.entrypoint.mappers.ConverterFeedback
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import java.lang.IllegalArgumentException
import java.util.*

@Controller("/feedbacks")
class FeedbackController(private val repository: FeedbackRepository) {

    @Get("/{id}")
    fun consultar(@PathVariable id: UUID): HttpResponse<Any>{
        val response = repository.findById(id).orElseThrow {
            IllegalArgumentException("Nenhum resultado encontrado com o id informado")
        }
        return HttpResponse.ok(ConverterFeedback.feedbackEntityToFeedbackDto(response))
    }

    @Get("/listar")
    fun listar(): HttpResponse<Any>{
        return HttpResponse.ok(ConverterFeedback.feedbackEntityListToFeedbackDtoList(repository.findAll()))
    }

}