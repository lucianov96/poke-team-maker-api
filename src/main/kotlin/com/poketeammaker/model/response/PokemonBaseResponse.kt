package com.poketeammaker.model.response

import com.poketeammaker.entity.Pokemon
import com.poketeammaker.model.Stat

class PokemonBaseResponse private constructor(
    val id: Long,
    val name: String,
    val abilities: List<String>,
    val types: List<String>,
    val stats: List<Stat>
) {
    data class Builder(
        private var id: Long? = null,
        private var name: String? = null,
        private var abilities: List<String>? = null,
        private var types: List<String>? = null,
        private var stats: List<Stat>? = null
    ) {
        fun from(pokemon: Pokemon): Builder {
            apply { this.id = pokemon.id }
            apply { this.name = pokemon.name.capitalize() }
            apply { this.abilities = buildAbilities(pokemon) }
            apply { this.types = buildTypes(pokemon) }
            apply { this.stats = buildStats(pokemon) }
            return this
        }
        fun build() = PokemonBaseResponse(id!!, name!!, abilities!!, types!!, stats!!)
        private fun buildStats(pokemon: Pokemon): List<Stat> {
            return listOf(
                Stat("PS", pokemon.hp),
                Stat("Attack", pokemon.attack),
                Stat("Defense", pokemon.defense),
                Stat("Sp. Attack", pokemon.spAttack),
                Stat("Sp. Defense", pokemon.spDefense),
                Stat("Speed", pokemon.speed)
            )
        }

        private fun buildAbilities(pokemon: Pokemon): List<String> {
            val list = mutableListOf(pokemon.ability_1)
            if (pokemon.ability_2 != null) list.add(pokemon.ability_2!!)
            return list
        }

        private fun buildTypes(pokemon: Pokemon): List<String> {
            val list = mutableListOf(pokemon.type_1)
            if (pokemon.type_2 != null) list.add(pokemon.type_2!!)
            return list
        }
    }
}
