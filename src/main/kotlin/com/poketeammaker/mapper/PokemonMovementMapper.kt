package com.poketeammaker.mapper

import com.poketeammaker.model.dto.PokemonMovementDTO
import com.poketeammaker.model.entity.Movement
import org.springframework.stereotype.Component

@Component
class PokemonMovementMapper {

    fun toDTO(movement: Movement) =
        with(movement) {
            PokemonMovementDTO(
                name,
                type,
                movementType,
                points?.toInt(),
                accuracy?.toInt(),
                priority.toInt(),
            )
        }
}