package com.poketeammaker.model

import com.poketeammaker.entity.PokemonCatchWay
import com.poketeammaker.model.response.PokemonCatchWayBaseResponse

data class PokemonCatch (
    val pokemonVersion: String,
    val location: String,
    val way: String
) {
    data class Builder(
        private var pokemonVersion: String? = null,
        private var location: String? = null,
        private var way: String? = null
    ) {
        fun from(pokemonCatchWay: PokemonCatchWay): Builder {
            apply { this.pokemonVersion = pokemonCatchWay.pokemonVersion }
            apply { this.location = pokemonCatchWay.location }
            apply { this.way = pokemonCatchWay.way }
            return this
        }
        fun build() = PokemonCatch(pokemonVersion!!, location!!, way!!)
    }
}