package com.poketeammaker.service

import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.mapper.MainPokemonMapper
import com.poketeammaker.mapper.PokemonMapper
import com.poketeammaker.model.dto.MainPokemonDTO
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.dto.PokemonDTO
import com.poketeammaker.utils.buildDynamicQueryCondition
import org.springframework.stereotype.Service

@Service
class PokemonService(
    val pokemonDAO: PokemonDAO,
    val mainPokemonMapper: MainPokemonMapper,
    val pokemonMapper: PokemonMapper,
) {

    fun getPokemon(id: Long): PokemonDTO =
        pokemonMapper.toDTO(
            pokemonDAO.findById(id).get()
        )

    fun getPokemonList(queryParams: List<QueryParam>): List<MainPokemonDTO> =
        if (queryParams.isNotEmpty()) {
            val queryConditions = buildDynamicQueryCondition(queryParams)
            pokemonDAO.getPokemonFilteredList(queryConditions).map (
                mainPokemonMapper::toDTO
            )
        } else {
            pokemonDAO.findAll().toList().map(
                mainPokemonMapper::toDTO
            )
        }
}
