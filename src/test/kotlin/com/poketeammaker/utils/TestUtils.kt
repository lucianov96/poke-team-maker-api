package com.poketeammaker.utils

import com.poketeammaker.entity.Movement
import com.poketeammaker.entity.Pokemon
import com.poketeammaker.entity.PokemonCatchWay
import com.poketeammaker.enum.Type.FIGHTING

const val POKEMON_ID = 1L

fun createPokemon(
    id: Long = POKEMON_ID,
    name: String = "mockmon",
    ability1: String = "superpokemon",
    ability2: String? = null,
    type1: String = FIGHTING.name.toLowerCase(),
    type2: String? = null,
    hp: Long = 0,
    attack: Long = 0,
    defense: Long = 0,
    spAttack: Long = 0,
    spDefense: Long = 0,
    speed: Long = 0,
) = Pokemon(id, name, ability1, ability2, type1, type2, hp, attack, defense, spAttack, spDefense, speed)

fun createMovement(
    id: Long = 0,
    name: String = "",
    type: String = "",
    movementType: String = "",
    points: Long? = null,
    accuracy: Long? = null,
    priority: Long = 0
) = Movement(id, name, type, movementType, points, accuracy, priority)

fun createPokemonCatchWay(
    id: Long = 0,
    idPokemon: Long = 0,
    pokemonVersion: String = "",
    location: String = "",
    way: String = ""
) = PokemonCatchWay(id, idPokemon, pokemonVersion, location, way)
