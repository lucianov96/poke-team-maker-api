package com.poketeammaker.service

import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.mapper.PokemonCatchWayMapper
import com.poketeammaker.model.dto.PokemonCatchWayDTO
import org.springframework.stereotype.Service

@Service
class PokemonCatchWayService(
    val pokemonCatchWayDAO: PokemonCatchWayDAO,
    val pokemonCatchWayMapper: PokemonCatchWayMapper,
) {

    fun getPokemonCatchWays(id: Long): List<PokemonCatchWayDTO> =
        pokemonCatchWayDAO.findByIdPokemon(id).map (
            pokemonCatchWayMapper::toDTO
        )
}
