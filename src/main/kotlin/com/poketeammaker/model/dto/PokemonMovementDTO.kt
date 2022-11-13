package com.poketeammaker.model.dto

data class PokemonMovementDTO (
    val name: String,
    val type: String,
    val movementType: String,
    val points: Int? = null,
    val accuracy: Int? = null,
    val priority: Int = 0,
)
