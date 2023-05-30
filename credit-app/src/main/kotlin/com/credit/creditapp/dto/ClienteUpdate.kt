package com.credit.creditapp.dto

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Endereco
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ClienteUpdate(

    @field:NotBlank
    val primeiroNome: String,

    @field:NotBlank
    val ultimoNome: String,

    @field:NotNull
    @field:PositiveOrZero
    val renda: BigDecimal,

    @field:NotBlank
    @field:Pattern(regexp = "[0-9]{8}")
    val cep: String,

    @field:NotBlank
    val logradouro: String
) {
    fun toModel(cliente: Cliente): Cliente {
        cliente.primeiroNome = this.primeiroNome
        cliente.ultimoNome = this.ultimoNome
        cliente.renda = this.renda
        cliente.endereco.cep = this.cep
        cliente.endereco.logradouro = this.logradouro

        return cliente
    }
}
