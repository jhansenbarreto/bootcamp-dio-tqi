package com.credit.creditapp.exception

data class RegraNegocioException(override val message: String?) : RuntimeException(message)