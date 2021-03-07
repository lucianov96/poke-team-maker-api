package com.poketeammaker.model.response

import com.poketeammaker.model.PokemonCatch

class PokemonCatchWayBaseResponse private constructor(
    val pokemonCatchWays: List<PokemonCatch>
) {
    data class Builder(
        private var pokemonCatchWays: List<PokemonCatch> = mutableListOf()
    ) {
        fun from(pokemonCatchWays: List<PokemonCatch>): Builder {
            apply { this.pokemonCatchWays = pokemonCatchWays }
            return this
        }
        fun build() = PokemonCatchWayBaseResponse(pokemonCatchWays)
    }
}
