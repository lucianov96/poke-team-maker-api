package com.poketeammaker.enum

enum class QueryCondition(val dbCondition: String) {
    LESS_THAN("<"),
    EQUAL("="),
    GREATER_THAN(">");

    companion object {
        fun queryParamOf(value: String): String {
            return try {
                valueOf(value.toUpperCase()).dbCondition
            } catch (e: Exception) { "?" }
        }

        fun all(): String {
            return "${LESS_THAN.name.toLowerCase()}, " +
                "${EQUAL.name.toLowerCase()}, " +
                "${GREATER_THAN.name.toLowerCase()} "
        }
    }
}
