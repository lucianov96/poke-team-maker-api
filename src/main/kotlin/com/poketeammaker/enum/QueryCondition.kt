package com.poketeammaker.enum

enum class QueryCondition(val dbCondition: String) {
    LESS_THAN("<"),
    EQUAL("="),
    GREATER_THAN(">");

    companion object {
        const val REG_EXP = "(LESS_THAN|EQUAL|GREATER_THAN)"
        fun queryParamOf(value: String): String {
            return try {
                valueOf(value.toUpperCase()).dbCondition
            } catch (e: Exception) { "?" }
        }
    }
}
