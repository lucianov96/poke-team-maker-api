package com.poketeammaker.unit.service

import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.mapper.PokemonMovementMapper
import com.poketeammaker.service.PokemonService
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.createCorrectQueryParamList
import com.poketeammaker.utils.createMovement
import com.poketeammaker.utils.createPokemon
import com.poketeammaker.utils.createPokemonCatchWay
import com.poketeammaker.utils.createQueryParamList
import com.poketeammaker.utils.title
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional

class PokemonServiceTest {

    private val expectedPokemon = createPokemon()
    private val expectedPokemonList = mutableListOf(
        expectedPokemon,
        createPokemon(2, "mockmon2")
    )

    private val expectedPokemonCatchWayList = mutableListOf(
        createPokemonCatchWay(pokemonVersion = "yellow"),
        createPokemonCatchWay(way = "gift"),
    )

    private val expectedMovementList = mutableListOf(
        createMovement(1, "razor-wind", "normal"),
        createMovement(2, "razor-wind-2", "electric")
    )

    private val queryParamList = createCorrectQueryParamList()
    private val emptyQueryParamList = createQueryParamList()

    private val pokemonDAO: PokemonDAO = mockk()
    private val pokemonCatchWayDAO: PokemonCatchWayDAO = mockk()
    private val movementDAO: MovementDAO = mockk()
    private val mainPokemonMapper = MainPokemonMapper()
    private val pokemonMapper = PokemonMapper()
    private val pokemonCatchWayMapper = PokemonCatchWayMapper()
    private val pokemonMovementMapper = PokemonMovementMapper()

    private val pokemonService = PokemonService(
        pokemonDAO,
        pokemonCatchWayDAO,
        movementDAO,
        mainPokemonMapper,
        pokemonMapper,
        pokemonCatchWayMapper,
        pokemonMovementMapper
    )

    @Test
    fun getPokemonListOk() {
        // GIVEN
        every { pokemonDAO.findAll() } returns expectedPokemonList

        // WHEN
        val pokemonList = pokemonService.getPokemonList()

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
        assertThat(pokemon.name).isEqualTo(expectedPokemon.name)
        assertThat(pokemon.types[0]).isEqualTo(expectedPokemon.type_1)
        assertThat(pokemon.abilities[0]).isEqualTo(expectedPokemon.ability_1)
        assertThat(pokemon.stats.attack).isEqualTo(expectedPokemon.attack)
        assertThat(pokemon.stats.defense).isEqualTo(expectedPokemon.defense)
        assertThat(pokemon.stats.spAttack).isEqualTo(expectedPokemon.spAttack)
        assertThat(pokemon.stats.spDefense).isEqualTo(expectedPokemon.spDefense)
        assertThat(pokemon.stats.speed).isEqualTo(expectedPokemon.speed)
    }

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

    @Test
    fun getPokemonMovementsOk() {
        // GIVEN
        every { movementDAO.findByPokemon(POKEMON_ID) } returns expectedMovementList

        // WHEN
        val movementList = pokemonService.getPokemonMovements(POKEMON_ID)

        // THEN
        verify { movementDAO.findByPokemon(POKEMON_ID) }
        assertThat(movementList[0].name).isEqualTo(expectedMovementList[0].name)
        assertThat(movementList[0].type).isEqualTo(expectedMovementList[0].type)
        assertThat(movementList[1].name).isEqualTo(expectedMovementList[1].name)
        assertThat(movementList[1].type).isEqualTo(expectedMovementList[1].type)
    }

    @Test
    fun getPokemonFilteredListOk() {
        // GIVEN
        every { pokemonDAO.getPokemonFilteredList(any()) } returns expectedPokemonList

        // WHEN
        val pokemonList = pokemonService.getPokemonFilteredList(queryParamList)

        // THEN
        verify { pokemonDAO.getPokemonFilteredList(any()) }
        assertThat(pokemonList[0].id).isEqualTo(expectedPokemonList[0].id)
        assertThat(pokemonList[0].name).isEqualTo(expectedPokemonList[0].name.title())
        assertThat(pokemonList[1].id).isEqualTo(expectedPokemonList[1].id)
        assertThat(pokemonList[1].name).isEqualTo(expectedPokemonList[1].name.title())
    }

    @Test
    fun getPokemonFilteredListBadRequest() {
        // WHEN - THEN
        assertThrows<BadRequestException> { pokemonService.getPokemonFilteredList(emptyQueryParamList) }
    }
}
