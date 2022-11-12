package com.poketeammaker.dao

import com.poketeammaker.model.entity.Pokemon

interface PokemonDAOCustom {
    fun getPokemonFilteredList(queryConditions: String): List<Pokemon>
}
