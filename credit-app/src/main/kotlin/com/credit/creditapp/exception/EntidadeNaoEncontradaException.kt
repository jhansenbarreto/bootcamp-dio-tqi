package com.credit.creditapp.exception

data class EntidadeNaoEncontradaException(override val message: String?) : RuntimeException(message)