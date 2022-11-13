package com.poketeammaker.unit.service

import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.mapper.PokemonMovementMapper
import com.poketeammaker.service.MovementService
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

class MovementServiceTest {

    private val expectedMovementList = mutableListOf(
        createMovement(1, "razor-wind", "normal"),
        createMovement(2, "razor-wind-2", "electric")
    )

    private val movementDAO: MovementDAO = mockk()
    private val pokemonMovementMapper = PokemonMovementMapper()

    private val movementService = MovementService(
        movementDAO,
        pokemonMovementMapper
    )

    @Test
    fun getPokemonMovementsOk() {
        // GIVEN
        every { movementDAO.findByPokemon(POKEMON_ID) } returns expectedMovementList

        // WHEN
        val movementList = movementService.getPokemonMovements(POKEMON_ID)

        // THEN
        verify { movementDAO.findByPokemon(POKEMON_ID) }
        assertThat(movementList[0].name).isEqualTo(expectedMovementList[0].name)
        assertThat(movementList[0].type).isEqualTo(expectedMovementList[0].type)
        assertThat(movementList[1].name).isEqualTo(expectedMovementList[1].name)
        assertThat(movementList[1].type).isEqualTo(expectedMovementList[1].type)
    }

}
