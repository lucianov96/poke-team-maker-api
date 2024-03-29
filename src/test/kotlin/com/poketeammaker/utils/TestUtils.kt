package com.poketeammaker.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.poketeammaker.model.entity.Movement
import com.poketeammaker.model.entity.Pokemon
import com.poketeammaker.model.entity.PokemonCatchWay
import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.enum.QueryCondition.EQUAL
import com.poketeammaker.enum.Type.ELECTRIC
import com.poketeammaker.enum.Type.FIGHTING
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.request.PokemonFilterRequest
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
    type1: String = FIGHTING.name.lowercase(),
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

fun createCorrectFilterRequest() = createPokemonFilterRequest(
    "Dragonite",
    FIGHTING.name,
    ELECTRIC.name,
    "Inner Focus",
    "Inner Focus",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100"
)

fun createPokemonFilterRequest(
    name: String? = null,
    type1: String? = null,
    type2: String? = null,
    ability1: String? = null,
    ability2: String? = null,
    ps: String? = null,
    psValue: String? = null,
    attack: String? = null,
    attackValue: String? = null,
    defense: String? = null,
    defenseValue: String? = null,
    spAttack: String? = null,
    spAttackValue: String? = null,
    spDefense: String? = null,
    spDefenseValue: String? = null,
    speed: String? = null,
    speedValue: String? = null
) = PokemonFilterRequest(
    name,
    type1,
    type2,
    ability1,
    ability2,
    ps,
    psValue,
    attack,
    attackValue,
    defense,
    defenseValue,
    spAttack,
    spAttackValue,
    spDefense,
    spDefenseValue,
    speed,
    speedValue
)

fun createCorrectQueryParamList() = createQueryParamList(
    "Dragonite",
    FIGHTING.name,
    ELECTRIC.name,
    "Inner Focus",
    "Inner Focus",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
    EQUAL.name,
    "100",
)

fun createQueryParamList(
    name: String? = null,
    type1: String? = null,
    type2: String? = null,
    ability1: String? = null,
    ability2: String? = null,
    ps: String? = null,
    psValue: String? = null,
    attack: String? = null,
    attackValue: String? = null,
    defense: String? = null,
    defenseValue: String? = null,
    spAttack: String? = null,
    spAttackValue: String? = null,
    spDefense: String? = null,
    spDefenseValue: String? = null,
    speed: String? = null,
    speedValue: String? = null
) = listOf(
    QueryParam("name", "=", name ?: ""),
    QueryParam("ability_1", "=", ability1 ?: ""),
    QueryParam("ability_2", "=", ability2 ?: ""),
    QueryParam("type_1", "=", type1 ?: ""),
    QueryParam("type_2", "=", type2 ?: ""),
    QueryParam("hp", QueryCondition.queryParamOf(ps ?: ""), psValue ?: ""),
    QueryParam("attack", QueryCondition.queryParamOf(attack ?: ""), attackValue ?: ""),
    QueryParam("defense", QueryCondition.queryParamOf(defense ?: ""), defenseValue ?: ""),
    QueryParam("sp_attack", QueryCondition.queryParamOf(spAttack ?: ""), spAttackValue ?: ""),
    QueryParam("sp_defense", QueryCondition.queryParamOf(spDefense ?: ""), spDefenseValue ?: ""),
    QueryParam("speed", QueryCondition.queryParamOf(speed ?: ""), speedValue ?: "")
).filter { it.condition != "" && it.value != ""}

fun String.title() = this.replaceFirstChar(Char::titlecase)
