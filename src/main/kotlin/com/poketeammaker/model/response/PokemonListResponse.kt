package com.poketeammaker.model.response

import com.poketeammaker.model.MainPokemon

class PokemonListResponse private constructor(
    val pokemonList: List<MainPokemon>
) {
    data class Builder(
        private var pokemonList: List<MainPokemon>? = null
    ) {
        fun from(pokemonList: List<MainPokemon>): Builder {
            apply { this.pokemonList = pokemonList }
            return this
        }
        fun build() = PokemonListResponse(pokemonList!!)
    }
}
