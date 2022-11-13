package com.poketeammaker.service

import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.mapper.PokemonMovementMapper
import com.poketeammaker.model.dto.PokemonMovementDTO
import org.springframework.stereotype.Service

@Service
class MovementService(
    val movementDAO: MovementDAO,
    val pokemonMovementMapper: PokemonMovementMapper
) {

    fun getPokemonMovements(id: Long): List<PokemonMovementDTO> =
        movementDAO.findByPokemon(id).map (
            pokemonMovementMapper::toDTO
        )
}
