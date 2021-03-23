package com.poketeammaker.exception

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [(BadRequestException::class)])
    fun handleBadRequestException(e: BadRequestException) {
        throw ResponseStatusException(
            e.status,
            e.message
        )
    }

    @ExceptionHandler(value = [(IllegalArgumentException::class)])
    fun handleIllegalArgumentException(e: IllegalArgumentException) {
        throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            e.message
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST, reason = "dsadsa")
    @ExceptionHandler(value = [(BindException::class)])
    fun handleMethodArgumentNotValidException(e: BindException) {
        throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "ads"
        )
    }
}
