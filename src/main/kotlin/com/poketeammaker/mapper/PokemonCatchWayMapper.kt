package com.poketeammaker.mapper

import com.poketeammaker.model.dto.PokemonCatchWayDTO
import com.poketeammaker.model.entity.PokemonCatchWay
import org.springframework.stereotype.Component

@Component
class PokemonCatchWayMapper {
    fun toDTO(pokemonCatchWay: PokemonCatchWay) =
        with(pokemonCatchWay) {
            PokemonCatchWayDTO(
                pokemonVersion,
                location,
                way
            )
        }
}