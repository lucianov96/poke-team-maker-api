package com.poketeammaker.integration

import com.poketeammaker.enum.Type.FIGHTING
import com.poketeammaker.model.response.PokemonBaseResponse
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

        val response = objectMapper.readValue(resp.body ?: "{}", PokemonBaseResponse::class.java)
        val expectedBody = formatJson(objectMapper, pokemonBody, PokemonBaseResponse::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body).isEqualTo(expectedBody)
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
}
