package com.poketeammaker.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.CascadeType.ALL
import javax.persistence.JoinColumn
import javax.persistence.JoinTable

@Entity
data class Movement (

    @Id
    var id: Long = 0,
    var name: String = "",
    var type: String = "",
    var movementType: String = "",
    var points: Long? = null,
    var accuracy: Long? = null,
    var priority: Long = 0,

    @ManyToMany(cascade =  [ALL] )
    @JoinTable(
        name = "pokemon_movement",
        joinColumns = [JoinColumn(name = "id_movement")],
        inverseJoinColumns = [JoinColumn(name = "id_pokemon") ]
    )
    var pokemons: List<Pokemon> = mutableListOf()

)