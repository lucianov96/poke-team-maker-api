package com.poketeammaker.utils

import com.poketeammaker.model.QueryParam

fun buildDynamicQueryCondition(conditions: List<QueryParam>): String {
    var dynamicQueryCondition = ""
    conditions.forEach {
        dynamicQueryCondition += if (it.value.toIntOrNull() == null) {
            " AND " + it.field + " " + it.condition + " '" + it.value + "'"
        } else {
            " AND " + it.field + " " + it.condition + " " + it.value
        }
    }
    return dynamicQueryCondition.replaceFirst(" AND ", "")
}
