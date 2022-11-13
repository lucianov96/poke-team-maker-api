package com.poketeammaker.unit.service

import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.service.PokemonService
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.createCorrectQueryParamList
import com.poketeammaker.utils.createPokemon
import com.poketeammaker.utils.createQueryParamList
import com.poketeammaker.utils.title
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.Optional

class PokemonServiceTest {

    private val expectedPokemon = createPokemon()
    private val expectedPokemonList = mutableListOf(
        expectedPokemon,
        createPokemon(2, "mockmon2")
    )

    private val queryParamList = createCorrectQueryParamList()
    private val emptyQueryParamList = createQueryParamList()

    private val pokemonDAO: PokemonDAO = mockk()
    private val mainPokemonMapper = MainPokemonMapper()
    private val pokemonMapper = PokemonMapper()

    private val pokemonService = PokemonService(
        pokemonDAO,
        mainPokemonMapper,
        pokemonMapper
    )

    @Test
    fun getPokemonListOk() {
        // GIVEN
        every { pokemonDAO.findAll() } returns expectedPokemonList

        // WHEN
        val pokemonList = pokemonService.getPokemonList(emptyQueryParamList)

        // THEN
        verify { pokemonDAO.findAll() }
        assertThat(pokemonList[0].id).isEqualTo(expectedPokemonList[0].id)
        assertThat(pokemonList[0].name).isEqualTo(expectedPokemonList[0].name.title())
        assertThat(pokemonList[1].id).isEqualTo(expectedPokemonList[1].id)
        assertThat(pokemonList[1].name).isEqualTo(expectedPokemonList[1].name.title())
    }

    @Test
    fun getPokemonOk() {
        // GIVEN
        every { pokemonDAO.findById(POKEMON_ID) } returns Optional.of(expectedPokemon)

        // WHEN
        val pokemon = pokemonService.getPokemon(POKEMON_ID)

        // THEN
        verify { pokemonDAO.findById(POKEMON_ID) }
        assertThat(pokemon.id).isEqualTo(expectedPokemon.id)
        assertThat(pokemon.name).isEqualTo(expectedPokemon.name.title())
        assertThat(pokemon.types[0]).isEqualTo(expectedPokemon.type_1)
        assertThat(pokemon.abilities[0]).isEqualTo(expectedPokemon.ability_1)
        assertThat(pokemon.stats.attack).isEqualTo(expectedPokemon.attack)
        assertThat(pokemon.stats.defense).isEqualTo(expectedPokemon.defense)
        assertThat(pokemon.stats.spAttack).isEqualTo(expectedPokemon.spAttack)
        assertThat(pokemon.stats.spDefense).isEqualTo(expectedPokemon.spDefense)
        assertThat(pokemon.stats.speed).isEqualTo(expectedPokemon.speed)
    }

    @Test
    fun getPokemonFilteredListOk() {
        // GIVEN
        every { pokemonDAO.getPokemonFilteredList(any()) } returns expectedPokemonList

        // WHEN
        val pokemonList = pokemonService.getPokemonList(queryParamList)

        // THEN
        verify { pokemonDAO.getPokemonFilteredList(any()) }
        assertThat(pokemonList[0].id).isEqualTo(expectedPokemonList[0].id)
        assertThat(pokemonList[0].name).isEqualTo(expectedPokemonList[0].name.title())
        assertThat(pokemonList[1].id).isEqualTo(expectedPokemonList[1].id)
        assertThat(pokemonList[1].name).isEqualTo(expectedPokemonList[1].name.title())
    }
}
