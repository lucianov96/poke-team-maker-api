package com.poketeammaker.model

import com.poketeammaker.entity.Pokemon

data class MainPokemon private constructor(
    val id: Long,
    val name: String
) {
    data class Builder(
        private var id: Long? = null,
        private var name: String? = null
    ) {
        fun from(pokemon: Pokemon): Builder {
            apply { this.id = pokemon.id }
            apply { this.name = pokemon.name }
            return this
        }
        fun build() = MainPokemon(this.id!!, this.name!!)
    }
}
