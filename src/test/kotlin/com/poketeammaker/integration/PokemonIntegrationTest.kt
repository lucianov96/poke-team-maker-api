package com.poketeammaker.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.enum.Type.FIGHTING
import com.poketeammaker.enum.Type.NORMAL
import com.poketeammaker.model.response.PokemonBaseResponse
import com.poketeammaker.model.response.PokemonCatchWayBaseResponse
import com.poketeammaker.model.response.PokemonListResponse
import com.poketeammaker.model.response.PokemonMovementBaseResponse
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.createMovement
import com.poketeammaker.utils.createPokemon
import com.poketeammaker.utils.createPokemonCatchWay
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.Optional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonIntegrationTest {

    companion object {
        private const val POKEMON_LIST_ENDPOINT = "/pokemon/list"
        private const val POKEMON_ENDPOINT = "/pokemon/$POKEMON_ID"
        private const val POKEMON_MOVEMENTS_ENDPOINT = "/pokemon/$POKEMON_ID/movements"
        private const val POKEMON_CATCHWAYS_ENDPOINT = "/pokemon/$POKEMON_ID/catch"
    }

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var pokemonDAO: PokemonDAO

    @MockkBean
    private lateinit var movementDAO: MovementDAO

    @MockkBean
    private lateinit var pokemonCatchWayDAO: PokemonCatchWayDAO

    private val pokemon = createPokemon(
        hp = 10,
        attack = 20,
        defense = 30,
        spAttack = 40,
        spDefense = 50,
        speed = 60
    )

    private val movement = createMovement(
        1,
        "razor-wind",
        "normal",
        "special",
        80,
        0,
        0
    )

    private val pokemonCatchWay = createPokemonCatchWay(
        pokemonVersion = "yellow",
        location = "cerulean-city-area",
        way = "gift"
    )

    private val pokemonList = mutableListOf(
        pokemon,
        createPokemon(2, "mockmon2")
    )

    private val movementList = mutableListOf(
        movement,
        movement.copy(
            name = "razor-wind-2",
            points = 70
        )
    )

    private val pokemonCatchWayList = mutableListOf(
        pokemonCatchWay,
        pokemonCatchWay.copy(
            pokemonVersion = "red"
        )
    )

    @Test
    fun testGetPokemonOk() {

        every { pokemonDAO.findById(POKEMON_ID) } returns Optional.of(pokemon)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonBaseResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.id).isEqualTo(1)
        assertThat(response.name).isEqualTo("Mockmon")
        assertThat(response.abilities[0]).isEqualTo("superpokemon")
        assertThat(response.types[0]).isEqualTo(FIGHTING.name.toLowerCase())
        assertThat(response.stats[0].value).isEqualTo(10)
        assertThat(response.stats[1].value).isEqualTo(20)
        assertThat(response.stats[2].value).isEqualTo(30)
        assertThat(response.stats[3].value).isEqualTo(40)
        assertThat(response.stats[4].value).isEqualTo(50)
        assertThat(response.stats[5].value).isEqualTo(60)
    }

    @Test
    fun testGetPokemonListOk() {

        every { pokemonDAO.findAll() } returns pokemonList

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_LIST_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonListResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.pokemonList[0].id).isEqualTo(1)
        assertThat(response.pokemonList[1].id).isEqualTo(2)
        assertThat(response.pokemonList[0].name).isEqualTo("mockmon")
        assertThat(response.pokemonList[1].name).isEqualTo("mockmon2")
    }

    @Test
    fun testGetMovementsOk() {

        every { movementDAO.findByPokemon(POKEMON_ID) } returns movementList

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_MOVEMENTS_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonMovementBaseResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.pokemonMovements[0].name).isEqualTo("razor-wind")
        assertThat(response.pokemonMovements[0].type).isEqualTo(NORMAL.name.toLowerCase())
        assertThat(response.pokemonMovements[0].movementType).isEqualTo("special")
        assertThat(response.pokemonMovements[0].points).isEqualTo(80)
        assertThat(response.pokemonMovements[0].accuracy).isEqualTo(0)
        assertThat(response.pokemonMovements[0].priority).isEqualTo(0)
        assertThat(response.pokemonMovements[1].name).isEqualTo("razor-wind-2")
        assertThat(response.pokemonMovements[1].points).isEqualTo(70)
    }

    @Test
    fun testGetPokemonCatchWaysOk() {

        every { pokemonCatchWayDAO.findByIdPokemon(POKEMON_ID) } returns pokemonCatchWayList

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_CATCHWAYS_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonCatchWayBaseResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.pokemonCatchWays[0].pokemonVersion).isEqualTo("yellow")
        assertThat(response.pokemonCatchWays[0].location).isEqualTo("cerulean-city-area")
        assertThat(response.pokemonCatchWays[0].way).isEqualTo("gift")
        assertThat(response.pokemonCatchWays[1].pokemonVersion).isEqualTo("red")
    }
}
