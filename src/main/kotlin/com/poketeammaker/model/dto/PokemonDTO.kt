package com.poketeammaker.model.dto

data class PokemonDTO (
    val id: Long,
    val name: String,
    val abilities: List<String>,
    val types: List<String>,
    val stats: StatsDTO
) {
    data class StatsDTO(
        val ps: Int,
        val attack: Int,
        val defense: Int,
        val spAttack: Int,
        val spDefense: Int,
        val speed: Int
    )
}
