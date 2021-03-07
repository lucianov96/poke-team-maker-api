package com.poketeammaker.model.response

import com.poketeammaker.model.PokemonMovement

class PokemonMovementBaseResponse private constructor(
    val pokemonMovements: List<PokemonMovement>
) {
    data class Builder(
        private var pokemonMovements: List<PokemonMovement> = mutableListOf()
    ) {
        fun from(pokemonMovements: List<PokemonMovement>): Builder {
            apply { this.pokemonMovements = pokemonMovements }
            return this
        }
        fun build() = PokemonMovementBaseResponse(pokemonMovements)
    }
}
