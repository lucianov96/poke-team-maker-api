package com.poketeammaker.exception

import java.lang.Exception

open class ApiException(val msg: String, val causex: Throwable?) : Exception(msg, causex)
