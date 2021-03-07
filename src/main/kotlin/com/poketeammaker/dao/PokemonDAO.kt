package com.poketeammaker.dao

import com.poketeammaker.entity.Pokemon
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PokemonDAO: CrudRepository<Pokemon, Long>, PokemonDAOCustom {
    override fun findById(id: Long): Optional<Pokemon>
}