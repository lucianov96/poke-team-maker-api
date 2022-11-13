package com.poketeammaker.service

import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.mapper.PokemonMovementMapper
import com.poketeammaker.model.dto.MainPokemonDTO
import com.poketeammaker.model.dto.PokemonCatchWayDTO
import com.poketeammaker.model.dto.PokemonMovementDTO
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.dto.PokemonDTO
import com.poketeammaker.utils.buildDynamicQueryCondition
import org.springframework.stereotype.Service

@Service
class PokemonService(
    val pokemonDAO: PokemonDAO,
    val pokemonCatchWayDAO: PokemonCatchWayDAO,
    val movementDAO: MovementDAO,
    val mainPokemonMapper: MainPokemonMapper,
    val pokemonMapper: PokemonMapper,
    val pokemonCatchWayMapper: PokemonCatchWayMapper,
    val pokemonMovementMapper: PokemonMovementMapper
) {

    fun getPokemonList(): List<MainPokemonDTO> =
        pokemonDAO.findAll().toList().map(
            mainPokemonMapper::toDTO
        )

    fun getPokemon(id: Long): PokemonDTO =
        pokemonMapper.toDTO(
            pokemonDAO.findById(id).get()
        )

    fun getPokemonCatchWays(id: Long): List<PokemonCatchWayDTO> =
        pokemonCatchWayDAO.findByIdPokemon(id).map (
            pokemonCatchWayMapper::toDTO
        )

    fun getPokemonMovements(id: Long): List<PokemonMovementDTO> =
        movementDAO.findByPokemon(id).map (
            pokemonMovementMapper::toDTO
        )

    fun getPokemonFilteredList(queryParams: List<QueryParam>): List<MainPokemonDTO> =
        if (!queryParams.isEmpty()) {
            val queryConditions = buildDynamicQueryCondition(queryParams)
            pokemonDAO.getPokemonFilteredList(queryConditions).map (
                mainPokemonMapper::toDTO
            )
        } else {
            throw BadRequestException("No filters specified to search Pokemons")
        }
}
