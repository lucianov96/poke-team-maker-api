package com.poketeammaker.mapper

import com.poketeammaker.model.dto.PokemonDTO
import com.poketeammaker.model.entity.Pokemon
import com.poketeammaker.utils.title
import org.springframework.stereotype.Component

@Component
class PokemonMapper {

    fun toDTO(pokemon: Pokemon) =
        with(pokemon) {
            PokemonDTO(
                id,
                name.title(),
                listOfNotNull(
                    ability_1,
                    ability_2
                ),
                listOfNotNull(
                    type_1,
                    type_2
                ),
                PokemonDTO.StatsDTO(
                    hp.toInt(),
                    attack.toInt(),
                    defense.toInt(),
                    spAttack.toInt(),
                    spDefense.toInt(),
                    speed.toInt()
                )
            )
        }
}