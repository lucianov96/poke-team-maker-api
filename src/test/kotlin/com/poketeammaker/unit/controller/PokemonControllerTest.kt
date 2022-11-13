package com.poketeammaker.unit.controller

import com.poketeammaker.controller.PokemonController
import com.poketeammaker.enum.QueryCondition.EQUAL
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.mapper.PokemonMovementMapper
import com.poketeammaker.service.MovementService
import com.poketeammaker.service.PokemonCatchWayService
import com.poketeammaker.service.PokemonService
import com.poketeammaker.utils.POKEMON_ID
import com.poketeammaker.utils.createCorrectFilterRequest
import com.poketeammaker.utils.createCorrectQueryParamList
import com.poketeammaker.utils.createMovement
import com.poketeammaker.utils.createPokemon
import com.poketeammaker.utils.createPokemonCatchWay
import com.poketeammaker.utils.createPokemonFilterRequest
import com.poketeammaker.utils.createQueryParamList
import com.poketeammaker.validator.RequestValidator
import io.mockk.MockKException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PokemonControllerTest {
    private val pokemonService: PokemonService = mockk()
    private val movementService: MovementService = mockk()
    private val pokemonCatchWayService: PokemonCatchWayService = mockk()
    private val requestValidator = RequestValidator()
    private val mainPokemonMapper = MainPokemonMapper()
    private val pokemonCatchWayMapper = PokemonCatchWayMapper()
    private val pokemonMapper = PokemonMapper()
    private val pokemonMovementMapper = PokemonMovementMapper()

    private val pokemonController = PokemonController(
        pokemonService,
        movementService,
        pokemonCatchWayService,
        requestValidator
    )

    private val expectedPokemon = pokemonMapper.toDTO(
        createPokemon(name = "Dragonite")
    )

    private val expectedMainPokemon = mainPokemonMapper.toDTO(
        createPokemon(name = "Dragonite")
    )

    private val expectedPokemons = listOf(
        expectedMainPokemon
    )

    private val expectedMovements = listOf(
        createMovement(name = "razor-wind"),
        createMovement(name = "razor-wind-2")
    ).map {
        pokemonMovementMapper.toDTO(it)
    }
    private val expectedCatchWays = listOf(
        createPokemonCatchWay(way = "gift"),
        createPokemonCatchWay(way = "walk")
    ).map {
        pokemonCatchWayMapper.toDTO(it)
    }
    private val emptyPokemonFilterRequest = createPokemonFilterRequest()
    private val pokemonFilterRequest = createCorrectFilterRequest()
    private val pokemonFilterBadTypeRequest = createPokemonFilterRequest(type1 = "asd")
    private val pokemonFilterBadConditionRequest = createPokemonFilterRequest(ps = "asd")
    private val pokemonFilterBadConditionWithoutValueRequest = createPokemonFilterRequest(ps = EQUAL.name)
    private val pokemonFilterBadValueWithoutConditionRequest = createPokemonFilterRequest(psValue = "100")
    private val queryParamList = createCorrectQueryParamList()
    private val emptyQueryParamList = createQueryParamList()

    @Test
    fun getPokemonByIdOk() {
        // GIVEN
        every { pokemonService.getPokemon(POKEMON_ID) } returns expectedPokemon

        // WHEN
        val pokemon = pokemonController.getPokemonById(POKEMON_ID.toString())

        // THEN
        verify { pokemonService.getPokemon(POKEMON_ID) }
        assertThat(pokemon.body?.name).isEqualTo(expectedPokemon.name)
    }

    @Test
    fun getPokemonMovementsByIdOk() {
        // GIVEN
        every { movementService.getPokemonMovements(POKEMON_ID) } returns expectedMovements

        // WHEN
        val movements = pokemonController.getPokemonMovementsById(POKEMON_ID.toString())

        // THEN
        verify { movementService.getPokemonMovements(POKEMON_ID) }
        assertThat(movements.body?.get(0)?.name).isEqualTo(expectedMovements[0].name)
        assertThat(movements.body?.get(1)?.name).isEqualTo(expectedMovements[1].name)
    }

    @Test
    fun getPokemonCatchWaysByIdOk() {
        // GIVEN
        every { pokemonCatchWayService.getPokemonCatchWays(POKEMON_ID) } returns expectedCatchWays

        // WHEN
        val catchWays = pokemonController.getPokemonCatchWaysById(POKEMON_ID.toString())

        // THEN
        verify { pokemonCatchWayService.getPokemonCatchWays(POKEMON_ID) }
        assertThat(catchWays.body?.get(0)?.way).isEqualTo(expectedCatchWays[0].way)
        assertThat(catchWays.body?.get(1)?.way).isEqualTo(expectedCatchWays[1].way)
    }

    @Test
    fun getPokemonListOk() {
        // GIVEN
        every { pokemonService.getPokemonList(emptyQueryParamList) } returns expectedPokemons

        // WHEN
        val pokemons = pokemonController.getList(emptyPokemonFilterRequest)

        // THEN
        verify { pokemonService.getPokemonList(emptyQueryParamList) }
        assertThat(pokemons.body?.get(0)?.name).isEqualTo(expectedPokemons[0].name)
    }

    @Test
    fun getPokemonFilteredListOk() {
        // GIVEN
        every { pokemonService.getPokemonList(queryParamList) } returns expectedPokemons

        // WHEN
        val pokemons = pokemonController.getList(pokemonFilterRequest)

        // THEN
        verify { pokemonService.getPokemonList(queryParamList) }
        assertThat(pokemons.body?.get(0)?.name).isEqualTo(expectedPokemons[0].name)
    }

    @Test
    fun getPokemonFilteredListBadType() {
        // WHEN - THEN
        assertThrows<MockKException> { pokemonController.getList(pokemonFilterBadTypeRequest) }
    }

    @Test
    fun getPokemonFilteredListBadCondition() {
        // WHEN - THEN
        assertThrows<BadRequestException> { pokemonController.getList(pokemonFilterBadConditionRequest) }
    }

    @Test
    fun getPokemonFilteredListConditionWithoutValue() {
        // WHEN - THEN
        assertThrows<BadRequestException> { pokemonController.getList(pokemonFilterBadConditionWithoutValueRequest) }
    }

    @Test
    fun getPokemonFilteredListValueWithoutCondition() {
        // WHEN - THEN
        assertThrows<BadRequestException> { pokemonController.getList(pokemonFilterBadValueWithoutConditionRequest) }
    }
}
