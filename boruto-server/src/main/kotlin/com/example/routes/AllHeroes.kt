package com.example.routes

import com.example.model.ApiResponse
import com.example.repository.HeroRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllHeroes() {

    val heroesRepository: HeroRepository by inject()

    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1

            require(page in 1..5)

            val apiResponse = heroesRepository.getAllHeroes(page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )

        } catch (e : NumberFormatException){
            call.respond(
                message = ApiResponse(success = false, message = "Only Number Allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e : IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Heroes not found."),
                status = HttpStatusCode.NotFound
            )
        }
    }
}