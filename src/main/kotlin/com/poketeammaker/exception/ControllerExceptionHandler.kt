package com.poketeammaker.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.UUID

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [(BadRequestException::class)])
    fun handleBadRequestException(e: BadRequestException): ResponseEntity<ApiError> {
        val error = ApiError(
            UUID.randomUUID().toString(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name,
            e.message ?: ""
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(IllegalArgumentException::class)])
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ApiError> {
        val error = ApiError(
            UUID.randomUUID().toString(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name,
            e.message ?: ""
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(BindException::class)])
    fun handleBindException(e: BindException): ResponseEntity<ApiError> {
        val error = ApiError(
            UUID.randomUUID().toString(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name,
            e.bindingResult.fieldErrors[0].defaultMessage ?: ""
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}
