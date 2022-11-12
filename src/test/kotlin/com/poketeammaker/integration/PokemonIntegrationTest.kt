package com.poketeammaker.integration

import com.poketeammaker.enum.Type.FIGHTING
import com.poketeammaker.model.dto.PokemonDTO
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.TestUtils.Companion.formatJson
import com.poketeammaker.utils.TestUtils.Companion.getFile
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.Optional

class PokemonIntegrationTest : AbstractIntegrationTest() {

    companion object {
        private const val POKEMON_ENDPOINT = "/pokemon/$POKEMON_ID"
    }

    @Test
    fun testGetPokemonOk() {

        val pokemonBody = getFile("pokemon-response.json")

        every { pokemonDAO.findById(POKEMON_ID) } returns Optional.of(pokemon)

        val resp: ResponseEntity<String> = restTemplate.exchange(
            POKEMON_ENDPOINT,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String::class.java
        )

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonDTO::class.java)
        val expectedBody = formatJson(objectMapper, pokemonBody, PokemonDTO::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body?.replace("mockmon", "Mockmon")).isEqualTo(expectedBody)
        assertThat(response.id).isEqualTo(1)
        assertThat(response.name).isEqualTo("mockmon")
        assertThat(response.abilities[0]).isEqualTo("superpokemon")
        assertThat(response.types[0]).isEqualTo(FIGHTING.name.lowercase())
        assertThat(response.stats.ps).isEqualTo(10)
        assertThat(response.stats.attack).isEqualTo(20)
        assertThat(response.stats.defense).isEqualTo(30)
        assertThat(response.stats.spAttack).isEqualTo(40)
        assertThat(response.stats.spDefense).isEqualTo(50)
        assertThat(response.stats.speed).isEqualTo(60)
    }
}
