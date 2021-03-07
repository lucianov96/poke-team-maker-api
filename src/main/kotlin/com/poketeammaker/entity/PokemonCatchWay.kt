package com.poketeammaker.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PokemonCatchWay(

    @Id
    var id: Long = 0,
    var idPokemon: Long = 0,
    var pokemonVersion: String = "",
    var location: String = "",
    var way: String = ""
)
