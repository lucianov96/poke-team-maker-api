package com.poketeammaker.service

import com.poketeammaker.dao.MovementDAO
import com.poketeammaker.dao.PokemonCatchWayDAO
import com.poketeammaker.dao.PokemonDAO
import com.poketeammaker.entity.Movement
import com.poketeammaker.entity.Pokemon
import com.poketeammaker.entity.PokemonCatchWay
import com.poketeammaker.model.MainPokemon
import com.poketeammaker.model.PokemonCatch
import com.poketeammaker.model.PokemonMovement
import com.poketeammaker.model.QueryParam
import com.poketeammaker.utils.buildDynamicQueryCondition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PokemonService {

    @Autowired
    private lateinit var pokemonDAO: PokemonDAO

    @Autowired
    private lateinit var pokemonCatchWayDAO: PokemonCatchWayDAO

    @Autowired
    private lateinit var movementDAO: MovementDAO

    fun getPokemonList(): List<MainPokemon> {
        return pokemonDAO.findAll().toList().map {
            MainPokemon.Builder().from(it).build()
        }
    }

    fun getPokemon(id: Long): Pokemon {
        return pokemonDAO.findById(id).get()
    }

    fun getPokemonCatchWays(id: Long): List<PokemonCatch> {
        return pokemonCatchWayDAO.findByIdPokemon(id).map {
            PokemonCatch.Builder().from(it).build()
        }
    }

    fun getPokemonMovements(id: Long): List<PokemonMovement> {
        return movementDAO.findByPokemon(id).map {
            PokemonMovement.Builder().from(it).build()
        }
    }

    fun getPokemonFilteredList(queryParams: List<QueryParam>): List<MainPokemon> {
        return if (!queryParams.isEmpty()) {
            val queryConditions = buildDynamicQueryCondition(queryParams)
            pokemonDAO.getPokemonFilteredList(queryConditions).map {
                MainPokemon.Builder().from(it).build()
            }
        } else {
            throw Exception("No filters specified to search Pokemons")
        }
    }
}