package com.poketeammaker.dao

import com.poketeammaker.entity.Movement
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovementDAO: CrudRepository<Movement, Long> {

    @Query("SELECT m FROM Movement m INNER JOIN m.pokemons p WHERE p.id = :idPokemon")
    fun findByPokemon(idPokemon: Long): List<Movement>

}