package com.poketeammaker.controller

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.response.PokemonBaseResponse
import com.poketeammaker.model.response.PokemonCatchWayBaseResponse
import com.poketeammaker.model.response.PokemonListResponse
import com.poketeammaker.model.response.PokemonMovementBaseResponse
import com.poketeammaker.service.PokemonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController {

    @Autowired
    private lateinit var pokemonService: PokemonService

    @GetMapping("/pokemon/{id}")
    fun getPokemonById(@PathVariable id: String): ResponseEntity<PokemonBaseResponse> {
        val pokemon = pokemonService.getPokemon(id.toLong())
        val response = PokemonBaseResponse.Builder().from(pokemon).build()
        return ResponseEntity(response, OK)
    }

    @GetMapping("/pokemon/{id}/movements")
    fun getPokemonMovementsById(@PathVariable id: String): ResponseEntity<PokemonMovementBaseResponse> {
        val pokemonMovements = pokemonService.getPokemonMovements(id.toLong())
        val response = PokemonMovementBaseResponse.Builder().from(pokemonMovements).build()
        return ResponseEntity(response, OK)
    }

    @GetMapping("/pokemon/{id}/catch")
    fun getPokemonCatchWaysById(@PathVariable id: String): ResponseEntity<PokemonCatchWayBaseResponse> {
        val pokemonCatchWays = pokemonService.getPokemonCatchWays(id.toLong())
        val response = PokemonCatchWayBaseResponse.Builder().from(pokemonCatchWays).build()
        return ResponseEntity(response, OK)
    }

    @GetMapping("/pokemon/list")
    fun list(): ResponseEntity<PokemonListResponse> {
        val pokemonList = pokemonService.getPokemonList()
        val response = PokemonListResponse.Builder().from(pokemonList).build()
        return ResponseEntity(response, OK)
    }

    @GetMapping("/pokemon/list/filter")
    fun getFilteredlist(
        @RequestParam(required = false) type1: String?,
        @RequestParam(required = false) type2: String?,
        @RequestParam(required = false) ability1: String?,
        @RequestParam(required = false) ability2: String?,
        @RequestParam(required = false) ps: String?,
        @RequestParam(required = false) psValue: String?,
        @RequestParam(required = false) attack: String?,
        @RequestParam(required = false) attackValue: String?,
        @RequestParam(required = false) defense: String?,
        @RequestParam(required = false) defenseValue: String?,
        @RequestParam(required = false) spAttack: String?,
        @RequestParam(required = false) spAttackValue: String?,
        @RequestParam(required = false) spDefense: String?,
        @RequestParam(required = false) spDefenseValue: String?,
        @RequestParam(required = false) speed: String?,
        @RequestParam(required = false) speedValue: String?,

    ): ResponseEntity<PokemonListResponse> {
        val queryParamList = listOf<QueryParam>(
            QueryParam(
                "ability_1",
                "=",
                ability1?:""
            ),
            QueryParam(
                "ability_2",
                "=",
                ability2?:""
            ),
            QueryParam(
                "type_1",
                "=",
                type1?:""
            ),
            QueryParam(
                "type_2",
                "=",
                type2?:""
            ),
            QueryParam(
                "hp",
                QueryCondition.queryParamOf(ps?:""),
                psValue?:""
            ),
            QueryParam(
                "attack",
                QueryCondition.queryParamOf(attack?:""),
                attackValue?:""
            ),
            QueryParam(
                "defense",
                QueryCondition.queryParamOf(defense?:""),
                defenseValue?:""
            ),
            QueryParam(
                "sp_attack",
                QueryCondition.queryParamOf(spAttack?:""),
                spAttackValue?:""
            ),
            QueryParam(
                "sp_defense",
                QueryCondition.queryParamOf(spDefense?:""),
                spDefenseValue?:""
            ),
            QueryParam(
                "speed",
                QueryCondition.queryParamOf(speed?:""),
                speedValue?:""
            )).filter { it.condition!="" }
            .filter { it.value!="" }

        val pokemonList = pokemonService.getPokemonFilteredList(queryParamList)
        val response = PokemonListResponse.Builder().from(pokemonList).build()
        return ResponseEntity(response, OK)
    }
}