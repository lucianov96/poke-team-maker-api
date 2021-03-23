package com.poketeammaker.model.request

import com.poketeammaker.enum.QueryCondition
import com.poketeammaker.enum.Type
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

data class PokemonFilterRequest(

    @field:Pattern(message = "type1 doesn't match allowed types", regexp = Type.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val type1: String?,

    @field:Pattern(message = "type2 doesn't match allowed types", regexp = Type.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val type2: String?,

    val ability1: String?,
    val ability2: String?,

    @field:Pattern(message = "ps doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val ps: String?,

    @field:Min(message = "ps min value is 0", value = 0)
    val psValue: String?,

    @field:Pattern(message = "attack doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val attack: String?,

    @field:Min(message = "attack min value is 0", value = 0)
    val attackValue: String?,

    @field:Pattern(message = "defense doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val defense: String?,

    @field:Min(message = "defense min value is 0", value = 0)
    val defenseValue: String?,

    @field:Pattern(message = "spAttack doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val spAttack: String?,

    @field:Min(message = "spAttack value min value is 0", value = 0)
    val spAttackValue: String?,

    @field:Pattern(message = "spDefense doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val spDefense: String?,

    @field:Min(message = "spDefense min value is 0", value = 0)
    val spDefenseValue: String?,

    @field:Pattern(message = "speed doesn't match allowed values", regexp = QueryCondition.REG_EXP, flags = [Pattern.Flag.CASE_INSENSITIVE])
    val speed: String?,

    @field:Min(message = "speed min value is 0", value = 0)
    val speedValue: String?
)
