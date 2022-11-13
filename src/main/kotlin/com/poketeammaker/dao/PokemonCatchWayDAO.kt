package com.poketeammaker.dao

import com.poketeammaker.model.entity.PokemonCatchWay
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonCatchWayDAO : CrudRepository<PokemonCatchWay, Long> {
    fun findByIdPokemon(idPokemon: Long): List<PokemonCatchWay>
}
