package com.poketeammaker.dao

import com.poketeammaker.entity.Pokemon

interface PokemonDAOCustom {
    fun getPokemonFilteredList(queryConditions: String): List<Pokemon>
}
