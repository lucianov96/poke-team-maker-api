package com.poketeammaker.model.request

data class PokemonFilterRequest(
    val type1: String?,
    val type2: String?,
    val ability1: String?,
    val ability2: String?,
    val ps: String?,
    val psValue: String?,
    val attack: String?,
    val attackValue: String?,
    val defense: String?,
    val defenseValue: String?,
    val spAttack: String?,
    val spAttackValue: String?,
    val spDefense: String?,
    val spDefenseValue: String?,
    val speed: String?,
    val speedValue: String?
)
