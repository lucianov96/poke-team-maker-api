package com.poketeammaker.exception

data class ApiError(
    val request: String,
    val code: Int,
    val status: String,
    val message: String,
)
