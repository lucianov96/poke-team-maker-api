package com.poketeammaker.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.poketeammaker.entity.Movement
import com.poketeammaker.entity.Pokemon
import com.poketeammaker.entity.PokemonCatchWay
import com.poketeammaker.enum.Type.FIGHTING
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

class TestUtils {
    companion object {
        fun getFile(fileDirectory: String): String {
            val stream = this::class.java.classLoader.getResourceAsStream(fileDirectory)
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8).trimIndent()
        }
        fun <T> formatJson(objectMapper: ObjectMapper, json: String, type: Class<T>): String {
            val entity = objectMapper.readValue(json, type)
            val formattedJson = objectMapper.writeValueAsString(entity)
            return formattedJson.replace("\\", "")
        }
    }
}

const val POKEMON_ID = 1L

fun buildQueryParam(url: String, queryParam: String, value: String, first: Boolean): String {
    return if (first) {
        "$url$queryParam=$value"
    } else {
        "$url&$queryParam=$value"
    }
}

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
