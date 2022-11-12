package com.poketeammaker.integration

import com.ninjasquad.springmockk.MockkBean
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.model.dto.PokemonCatchWayDTO
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.TestUtils.Companion.formatJson
import com.poketeammaker.utils.TestUtils.Companion.getFile
import com.poketeammaker.utils.createPokemonCatchWay
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CatchWayIntegrationTest : AbstractIntegrationTest() {

    companion object {
        private const val POKEMON_CATCHWAYS_ENDPOINT = "/pokemon/$POKEMON_ID/catch"
    }

    @MockkBean
    protected lateinit var pokemonCatchWayDAO: PokemonCatchWayDAO

    private val pokemonCatchWay = createPokemonCatchWay(
        pokemonVersion = "yellow",
        location = "cerulean-city-area",
        way = "gift"
    )

    private val pokemonCatchWayList = mutableListOf(
        pokemonCatchWay,
        pokemonCatchWay.copy(
            pokemonVersion = "red"
        )
    )

    @Test
    fun testGetPokemonCatchWaysOk() {

        val catchWayBody = getFile("pokemon-catch-ways-response.json")

        every { pokemonCatchWayDAO.findByIdPokemon(POKEMON_ID) } returns pokemonCatchWayList

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_CATCHWAYS_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", Array<PokemonCatchWayDTO>::class.java)
        val expectedBody = formatJson(objectMapper, catchWayBody, Array<PokemonCatchWayDTO>::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body).isEqualTo(expectedBody)
        assertThat(response[0].pokemonVersion).isEqualTo("yellow")
        assertThat(response[0].location).isEqualTo("cerulean-city-area")
        assertThat(response[0].way).isEqualTo("gift")
        assertThat(response[1].pokemonVersion).isEqualTo("red")
    }
}
