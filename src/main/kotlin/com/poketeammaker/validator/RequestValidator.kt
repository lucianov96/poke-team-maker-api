package com.poketeammaker.validator

import com.poketeammaker.exception.BadRequestException
import com.poketeammaker.model.request.PokemonFilterRequest
import org.springframework.stereotype.Component

@Component
class RequestValidator {

    fun validate(request: PokemonFilterRequest) {
        validateFiltersIntegration(request)
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
