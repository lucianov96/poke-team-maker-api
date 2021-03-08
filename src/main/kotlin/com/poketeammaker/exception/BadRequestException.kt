package com.poketeammaker.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(BAD_REQUEST)
class BadRequestException(message: String, cause: Throwable? = null, val status: HttpStatus = BAD_REQUEST) : RuntimeException(message, cause)
