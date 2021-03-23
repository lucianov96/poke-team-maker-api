package com.poketeammaker.validator

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.enum.Type
import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.model.request.PokemonFilterRequest
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class RequestValidator {

    fun validate(request: PokemonFilterRequest) {
        validateTypes(request)
        validateFilters(request)
        validateFilterValues(request)
        validateFiltersIntegration(request)
    }

    private fun validateTypes(request: PokemonFilterRequest) {
        try {
            if (!request.type1.isNullOrEmpty()) {
                Type.valueOf(request.type1.toUpperCase())
            }
            if (!request.type2.isNullOrEmpty()) {
                Type.valueOf(request.type2.toUpperCase())
            }
        } catch (e: Exception) {
            throw BadRequestException("Types must match the existing types: ${Type.all()} or must be null or empty", e)
        }
    }

    private fun validateFilters(request: PokemonFilterRequest) {
        try {
            if (!request.ps.isNullOrEmpty()) {
                QueryCondition.valueOf(request.ps.toUpperCase())
            }
            if (!request.attack.isNullOrEmpty()) {
                QueryCondition.valueOf(request.attack.toUpperCase())
            }
            if (!request.defense.isNullOrEmpty()) {
                QueryCondition.valueOf(request.defense.toUpperCase())
            }
            if (!request.spAttack.isNullOrEmpty()) {
                QueryCondition.valueOf(request.spAttack.toUpperCase())
            }
            if (!request.spDefense.isNullOrEmpty()) {
                QueryCondition.valueOf(request.spDefense.toUpperCase())
            }
            if (!request.speed.isNullOrEmpty()) {
                QueryCondition.valueOf(request.speed.toUpperCase())
            }
        } catch (e: Exception) {
            throw BadRequestException("Stats conditions must be ${QueryCondition.all()} or must be null or empty", e)
        }
    }

    private fun validateFilterValues(request: PokemonFilterRequest) {
        if ((!request.psValue.isNullOrEmpty() && request.psValue.toIntOrNull() == null) ||
            (!request.attackValue.isNullOrEmpty() && request.attackValue.toIntOrNull() == null) ||
            (!request.defenseValue.isNullOrEmpty() && request.defenseValue.toIntOrNull() == null) ||
            (!request.spAttackValue.isNullOrEmpty() && request.spAttackValue.toIntOrNull() == null) ||
            (!request.spDefenseValue.isNullOrEmpty() && request.spDefenseValue.toIntOrNull() == null) ||
            (!request.speedValue.isNullOrEmpty() && request.speedValue.toIntOrNull() == null)
        ) {
            throw BadRequestException("Stats values must be null, empty or numeric")
        }
    }

    private fun validateFiltersIntegration(request: PokemonFilterRequest) {
        if ((request.ps.isNullOrEmpty() && !request.psValue.isNullOrEmpty()) || (!request.ps.isNullOrEmpty() && request.psValue.isNullOrEmpty()) ||
            (request.attack.isNullOrEmpty() && !request.attackValue.isNullOrEmpty()) || (!request.attack.isNullOrEmpty() && request.attackValue.isNullOrEmpty()) ||
            (request.defense.isNullOrEmpty() && !request.defenseValue.isNullOrEmpty()) || (!request.defense.isNullOrEmpty() && request.defenseValue.isNullOrEmpty()) ||
            (request.spAttack.isNullOrEmpty() && !request.spAttackValue.isNullOrEmpty()) || (!request.spAttack.isNullOrEmpty() && request.spAttackValue.isNullOrEmpty()) ||
            (request.spDefense.isNullOrEmpty() && !request.spDefenseValue.isNullOrEmpty()) || (!request.spDefense.isNullOrEmpty() && request.spDefenseValue.isNullOrEmpty()) ||
            (request.speed.isNullOrEmpty() && !request.speedValue.isNullOrEmpty()) || (!request.speed.isNullOrEmpty() && request.speedValue.isNullOrEmpty())
        ) {
            throw BadRequestException("Stat condition can't come without its value or viceversa, or both can be null")
        }
    }
}
