package com.poketeammaker.validator

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.enum.Type
import com.poketeammaker.exception.BadRequestException
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class RequestValidator {

    fun validateTypes(type1: String?, type2: String?) {
        try {
            if (!type1.isNullOrEmpty()) {
                Type.valueOf(type1.toUpperCase())
            }
            if (!type2.isNullOrEmpty()) {
                Type.valueOf(type2.toUpperCase())
            }
        } catch (e: Exception) {
            throw BadRequestException("Types must match the existing types: ${Type.all()} or must be null or empty", e)
        }
    }

    fun validateFilters(ps: String?, attack: String?, defense: String?, spAttack: String?, spDefense: String?, speed: String?) {
        try {
            if (!ps.isNullOrEmpty()) {
                QueryCondition.valueOf(ps.toUpperCase())
            }
            if (!attack.isNullOrEmpty()) {
                QueryCondition.valueOf(attack.toUpperCase())
            }
            if (!defense.isNullOrEmpty()) {
                QueryCondition.valueOf(defense.toUpperCase())
            }
            if (!spAttack.isNullOrEmpty()) {
                QueryCondition.valueOf(spAttack.toUpperCase())
            }
            if (!spDefense.isNullOrEmpty()) {
                QueryCondition.valueOf(spDefense.toUpperCase())
            }
            if (!speed.isNullOrEmpty()) {
                QueryCondition.valueOf(speed.toUpperCase())
            }
        } catch (e: Exception) {
            throw BadRequestException("Stats conditions must be ${QueryCondition.all()} or must be null or empty", e)
        }
    }

    fun validateFilterValues(psValue: String?, attackValue: String?, defenseValue: String?, spAttackValue: String?, spDefenseValue: String?, speedValue: String?) {
        if ((!psValue.isNullOrEmpty() && psValue.toIntOrNull() == null) ||
            (!attackValue.isNullOrEmpty() && attackValue.toIntOrNull() == null) ||
            (!defenseValue.isNullOrEmpty() && defenseValue.toIntOrNull() == null) ||
            (!spAttackValue.isNullOrEmpty() && spAttackValue.toIntOrNull() == null) ||
            (!spDefenseValue.isNullOrEmpty() && spDefenseValue.toIntOrNull() == null) ||
            (!speedValue.isNullOrEmpty() && speedValue.toIntOrNull() == null)
        ) {
            throw BadRequestException("Stats values must be null, empty or numeric")
        }
    }

    fun validateFiltersIntegration(ps: String?, psValue: String?, attack: String?, attackValue: String?, defense: String?, defenseValue: String?, spAttack: String?, spAttackValue: String?, spDefense: String?, spDefenseValue: String?, speed: String?, speedValue: String?) {
        if ((ps.isNullOrEmpty() && !psValue.isNullOrEmpty()) || (!ps.isNullOrEmpty() && psValue.isNullOrEmpty()) ||
            (attack.isNullOrEmpty() && !attackValue.isNullOrEmpty()) || (!attack.isNullOrEmpty() && attackValue.isNullOrEmpty()) ||
            (defense.isNullOrEmpty() && !defenseValue.isNullOrEmpty()) || (!defense.isNullOrEmpty() && defenseValue.isNullOrEmpty()) ||
            (spAttack.isNullOrEmpty() && !spAttackValue.isNullOrEmpty()) || (!spAttack.isNullOrEmpty() && spAttackValue.isNullOrEmpty()) ||
            (spDefense.isNullOrEmpty() && !spDefenseValue.isNullOrEmpty()) || (!spDefense.isNullOrEmpty() && spDefenseValue.isNullOrEmpty()) ||
            (speed.isNullOrEmpty() && !speedValue.isNullOrEmpty()) || (!speed.isNullOrEmpty() && speedValue.isNullOrEmpty())
        ) {
            throw BadRequestException("Stat condition can't come without its value or viceversa, or both can be null")
        }
    }
}
