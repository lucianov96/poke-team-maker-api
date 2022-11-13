package com.poketeammaker.unit.service

import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.service.PokemonCatchWayService
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.createPokemonCatchWay
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PokemonCatchWayServiceTest {

    private val expectedPokemonCatchWayList = mutableListOf(
        createPokemonCatchWay(pokemonVersion = "yellow"),
        createPokemonCatchWay(way = "gift"),
    )

    private val pokemonCatchWayDAO: PokemonCatchWayDAO = mockk()
    private val pokemonCatchWayMapper = PokemonCatchWayMapper()

    private val pokemonService = PokemonCatchWayService(
        pokemonCatchWayDAO,
        pokemonCatchWayMapper,
    )

    @Test
    fun getPokemonCatchWaysOk() {
        // GIVEN
        every { pokemonCatchWayDAO.findByIdPokemon(POKEMON_ID) } returns expectedPokemonCatchWayList

        // WHEN
        val pokemonCatchWays = pokemonService.getPokemonCatchWays(POKEMON_ID)

        // THEN
        verify { pokemonCatchWayDAO.findByIdPokemon(POKEMON_ID) }
        assertThat(pokemonCatchWays[0].pokemonVersion).isEqualTo(expectedPokemonCatchWayList[0].pokemonVersion)
        assertThat(pokemonCatchWays[1].way).isEqualTo(expectedPokemonCatchWayList[1].way)
    }

}
