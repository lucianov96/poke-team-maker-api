package com.poketeammaker.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Pokemon(

    @Id
    var id: Long = 0,
    var name: String = "",
    var ability_1: String = "",
    var ability_2: String? = "",
    var type_1: String = "",
    var type_2: String? = null,
    var hp: Long = 0,
    var attack: Long = 0,
    var defense: Long = 0,
    var spAttack: Long = 0,
    var spDefense: Long = 0,
    var speed: Long = 0,

    @ManyToMany(mappedBy = "pokemons")
    var movements: List<Movement> = mutableListOf()
)
