package com.poketeammaker.controller

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.request.PokemonFilterRequest
import com.poketeammaker.model.response.PokemonBaseResponse
import com.poketeammaker.model.response.PokemonCatchWayBaseResponse
import com.poketeammaker.model.response.PokemonListResponse
import com.poketeammaker.model.response.PokemonMovementBaseResponse
import com.poketeammaker.service.PokemonService
import com.poketeammaker.validator.RequestValidator
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PokemonController(
    val pokemonService: PokemonService,
    val requestValidator: RequestValidator
) {

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
    fun getFilteredlist(@Valid request: PokemonFilterRequest): ResponseEntity<PokemonListResponse> {
        requestValidator.validate(request)
        val queryParamList = listOf<QueryParam>(
            QueryParam("ability_1", "=", request.ability1 ?: ""),
            QueryParam("ability_2", "=", request.ability2 ?: ""),
            QueryParam("type_1", "=", request.type1 ?: ""),
            QueryParam("type_2", "=", request.type2 ?: ""),
            QueryParam("hp", QueryCondition.queryParamOf(request.ps ?: ""), request.psValue ?: ""),
            QueryParam("attack", QueryCondition.queryParamOf(request.attack ?: ""), request.attackValue ?: ""),
            QueryParam("defense", QueryCondition.queryParamOf(request.defense ?: ""), request.defenseValue ?: ""),
            QueryParam("sp_attack", QueryCondition.queryParamOf(request.spAttack ?: ""), request.spAttackValue ?: ""),
            QueryParam("sp_defense", QueryCondition.queryParamOf(request.spDefense ?: ""), request.spDefenseValue ?: ""),
            QueryParam("speed", QueryCondition.queryParamOf(request.speed ?: ""), request.speedValue ?: "")
        ).filter { it.condition != "" && it.value != "" }

        val pokemonList = pokemonService.getPokemonFilteredList(queryParamList)
        val response = PokemonListResponse.Builder().from(pokemonList).build()
        return ResponseEntity(response, OK)
    }
}
