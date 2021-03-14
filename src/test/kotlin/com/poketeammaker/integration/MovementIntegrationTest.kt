package com.poketeammaker.integration

import com.ninjasquad.springmockk.MockkBean
import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.enum.Type.NORMAL
import com.poketeammaker.model.response.PokemonMovementBaseResponse
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.TestUtils.Companion.formatJson
import com.poketeammaker.utils.TestUtils.Companion.getFile
import com.poketeammaker.utils.createMovement
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MovementIntegrationTest : AbstractIntegrationTest() {

    companion object {
        private const val POKEMON_MOVEMENTS_ENDPOINT = "/pokemon/$POKEMON_ID/movements"
    }

    @MockkBean
    private lateinit var movementDAO: MovementDAO

    private val movement = createMovement(
        1,
        "razor-wind",
        "normal",
        "special",
        80,
        0,
        0
    )

    private val movementList = mutableListOf(
        movement,
        movement.copy(
            name = "razor-wind-2",
            points = 70
        )
    )

    @Test
    fun testGetMovementsOk() {

        val movementBody = getFile("movements-response.json")

        every { movementDAO.findByPokemon(POKEMON_ID) } returns movementList

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_MOVEMENTS_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonMovementBaseResponse::class.java)
        val expectedBody = formatJson(objectMapper, movementBody, PokemonMovementBaseResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body).isEqualTo(expectedBody)
        assertThat(response.pokemonMovements[0].name).isEqualTo("razor-wind")
        assertThat(response.pokemonMovements[0].type).isEqualTo(NORMAL.name.toLowerCase())
        assertThat(response.pokemonMovements[0].movementType).isEqualTo("special")
        assertThat(response.pokemonMovements[0].points).isEqualTo(80)
        assertThat(response.pokemonMovements[0].accuracy).isEqualTo(0)
        assertThat(response.pokemonMovements[0].priority).isEqualTo(0)
        assertThat(response.pokemonMovements[1].name).isEqualTo("razor-wind-2")
        assertThat(response.pokemonMovements[1].points).isEqualTo(70)
    }
}
