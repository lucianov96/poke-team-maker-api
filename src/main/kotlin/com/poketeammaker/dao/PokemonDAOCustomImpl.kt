package com.poketeammaker.dao

import com.poketeammaker.model.entity.Pokemon
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class PokemonDAOCustomImpl : PokemonDAOCustom {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getPokemonFilteredList(queryConditions: String): List<Pokemon> {
        val query = entityManager.createQuery("SELECT p FROM Pokemon p WHERE $queryConditions", Pokemon::class.java)
        return query.resultList
    }
}
