package com.credit.creditapp.exception

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DetalhesErros(
    val title: String,
    val timestamp: LocalDateTime,
    val status: Int,
    val message: String,
    val errors: List<OrigemErro>?
) {
    data class OrigemErro(
        val name: String,
        val message: String
    )
}
