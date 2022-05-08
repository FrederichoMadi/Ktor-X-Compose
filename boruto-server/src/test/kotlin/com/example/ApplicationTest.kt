package com.example

import com.example.model.ApiResponse
import com.example.repository.HeroRepository
import com.example.repository.NEXT_PAGE_KEY
import com.example.repository.PREV_PAGE_KEY
import io.ktor.http.*
import io.ktor.application.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.util.Identity.decode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject

class ApplicationTest {

    private val heroRepository : HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access root endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Welcome to Boruto API!", response.content)
            }
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access al heroes endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/boruto/heroes").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val actual = Json.decodeFromString<ApiResponse>(
                    response.content.toString()
                )
                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = null,
                    nextPage = 2,
                    heroes = heroRepository.page1,
                    lastUpdated = actual.lastUpdated
                )

                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query al pages, assert correct information` () {
        withTestApplication(Application::module) {
            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5,
            )

            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "/boruto/heroes?page=$page").apply {
                    assertEquals(
                        expected = HttpStatusCode.OK,
                        actual = response.status()
                    )
                    val actual = Json.decodeFromString<ApiResponse>(response.content.toString())


                    val expected = ApiResponse(
                        success = true,
                        message = "ok",
                        prevPage = calculatePage(page)["prevPage"],
                        nextPage = calculatePage(page)["nextPage"],
                        heroes = heroes[page - 1],
                        lastUpdated = actual.lastUpdated
                    )


                    assertEquals(expected, actual)
                }
            }
        }
    }

    private fun calculatePage(page : Int) : Map<String, Int?> {
        var prevPage : Int? = page
        var nextPage : Int? = page
        if(page in 1..4){
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..5){
            prevPage = prevPage?.minus(1)
        }
        if (page == 1){
            prevPage = null
        }
        if (page == 5){
            nextPage = null
        }

        return mapOf(
            PREV_PAGE_KEY to prevPage,
            NEXT_PAGE_KEY to nextPage
        )
    }
}