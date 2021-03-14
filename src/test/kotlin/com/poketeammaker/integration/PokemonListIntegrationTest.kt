package com.poketeammaker.integration

import com.poketeammaker.enum.QueryCondition.EQUAL
import com.poketeammaker.enum.QueryCondition.GREATER_THAN
import com.poketeammaker.enum.QueryCondition.LESS_THAN
import com.poketeammaker.enum.Type.ELECTRIC
import com.poketeammaker.enum.Type.FIRE
import com.poketeammaker.model.response.PokemonListResponse
import com.poketeammaker.utils.TestUtils.Companion.formatJson
import com.poketeammaker.utils.TestUtils.Companion.getFile
import com.poketeammaker.utils.buildQueryParam
import com.poketeammaker.utils.createPokemon
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PokemonListIntegrationTest : AbstractIntegrationTest() {

    companion object {
        private const val POKEMON_LIST_ENDPOINT = "/pokemon/list"
        private const val POKEMON_LIST_FILTERS_ENDPOINT = "/pokemon/list/filter?"
    }

    private val pokemonList = mutableListOf(
        pokemon,
        createPokemon(2, "mockmon2")
    )

    private val pokemonListBody = getFile("pokemon-list-response.json")

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
        val expectedBody = formatJson(objectMapper, pokemonListBody, PokemonListResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body).isEqualTo(expectedBody)
        assertThat(response.pokemonList[0].id).isEqualTo(1)
        assertThat(response.pokemonList[1].id).isEqualTo(2)
        assertThat(response.pokemonList[0].name).isEqualTo("mockmon")
        assertThat(response.pokemonList[1].name).isEqualTo("mockmon2")
    }

    @Test
    fun testGetPokemonListFiltersOk() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        var url: String = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "type1", ELECTRIC.name.toLowerCase(), true)
        url = buildQueryParam(url, "type2", FIRE.name.toLowerCase(), false)
        url = buildQueryParam(url, "ability1", "fake-ability", false)
        url = buildQueryParam(url, "ability2", "fake-ability-2", false)
        url = buildQueryParam(url, "ps", EQUAL.name.toLowerCase(), false)
        url = buildQueryParam(url, "psValue", "100", false)
        url = buildQueryParam(url, "attack", GREATER_THAN.name.toLowerCase(), false)
        url = buildQueryParam(url, "attackValue", "100", false)
        url = buildQueryParam(url, "defense", LESS_THAN.name.toLowerCase(), false)
        url = buildQueryParam(url, "defenseValue", "100", false)
        url = buildQueryParam(url, "spAttack", LESS_THAN.name.toLowerCase(), false)
        url = buildQueryParam(url, "spAttackValue", "100", false)
        url = buildQueryParam(url, "spDefense", LESS_THAN.name.toLowerCase(), false)
        url = buildQueryParam(url, "spDefenseValue", "100", false)
        url = buildQueryParam(url, "speed", LESS_THAN.name.toLowerCase(), false)
        url = buildQueryParam(url, "speedValue", "100", false)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonListResponse::class.java)
        val expectedBody = formatJson(objectMapper, pokemonListBody, PokemonListResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body).isEqualTo(expectedBody)
        assertThat(response.pokemonList[0].id).isEqualTo(1)
        assertThat(response.pokemonList[1].id).isEqualTo(2)
        assertThat(response.pokemonList[0].name).isEqualTo("mockmon")
        assertThat(response.pokemonList[1].name).isEqualTo("mockmon2")
    }

    @Test
    fun testGetPokemonListFiltersBadType() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        val url = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "type1", "wrong-type", true)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        assertThat(resp.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun testGetPokemonListFiltersBadConditionStat() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        val url = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "ps", "wrong-type", true)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        assertThat(resp.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun testGetPokemonListFiltersBadValueStat() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        val url = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "psValue", "wrong-type", true)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        assertThat(resp.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun testGetPokemonListFiltersBadValueWithoutCondition() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        val url = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "psValue", "100", true)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        assertThat(resp.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun testGetPokemonListFiltersBadConditionWithoutvalue() {

        every { pokemonDAO.getPokemonFilteredList(any()) } returns pokemonList

        val url = buildQueryParam(POKEMON_LIST_FILTERS_ENDPOINT, "ps", EQUAL.name.toLowerCase(), true)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        assertThat(resp.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}
