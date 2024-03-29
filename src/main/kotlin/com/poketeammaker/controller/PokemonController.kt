package com.poketeammaker.controller

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.model.QueryParam
import com.poketeammaker.model.dto.MainPokemonDTO
import com.poketeammaker.model.dto.PokemonCatchWayDTO
import com.poketeammaker.model.request.PokemonFilterRequest
import com.poketeammaker.model.dto.PokemonDTO
import com.poketeammaker.model.dto.PokemonMovementDTO
import com.poketeammaker.service.MovementService
import com.poketeammaker.service.PokemonCatchWayService
import com.poketeammaker.service.PokemonService
import com.poketeammaker.validator.RequestValidator
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"])
class PokemonController(
    val pokemonService: PokemonService,
    val movementService: MovementService,
    val pokemonCatchWayService: PokemonCatchWayService,
    val requestValidator: RequestValidator
) {

    @GetMapping("/pokemon/{id}")
    fun getPokemonById(@PathVariable id: String): ResponseEntity<PokemonDTO> {
        val pokemon = pokemonService.getPokemon(id.toLong())
        return ResponseEntity(pokemon, OK)
    }

    @GetMapping("/pokemon/{id}/movements")
    fun getPokemonMovementsById(@PathVariable id: String): ResponseEntity<List<PokemonMovementDTO>> {
        val pokemonMovements = movementService.getPokemonMovements(id.toLong())
        return ResponseEntity(pokemonMovements, OK)
    }

    @GetMapping("/pokemon/{id}/catch")
    fun getPokemonCatchWaysById(@PathVariable id: String): ResponseEntity<List<PokemonCatchWayDTO>> {
        val pokemonCatchWays = pokemonCatchWayService.getPokemonCatchWays(id.toLong())
        return ResponseEntity(pokemonCatchWays, OK)
    }

    @GetMapping("/pokemon")
    fun getList(@Valid request: PokemonFilterRequest): ResponseEntity<List<MainPokemonDTO>> {
        requestValidator.validate(request)
        val queryParamList = listOf<QueryParam>(
            QueryParam("name", "=", request.name ?: ""),
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

        val pokemonList = pokemonService.getPokemonList(queryParamList)
        return ResponseEntity(pokemonList, OK)
    }
}
