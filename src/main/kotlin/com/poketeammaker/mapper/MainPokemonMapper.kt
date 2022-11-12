package com.poketeammaker.mapper

import com.poketeammaker.model.dto.MainPokemonDTO
import com.poketeammaker.model.entity.Pokemon
import com.poketeammaker.utils.title
import org.springframework.stereotype.Component

@Component
class MainPokemonMapper {

    fun toDTO(pokemon: Pokemon) =
        with(pokemon) {
            MainPokemonDTO(
                id,
                name.title()
            )
        }

}