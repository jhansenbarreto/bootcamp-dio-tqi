package com.credit.creditapp.dto

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Credito
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate

data class CreditoInput(

    @field:NotNull
    @field:Positive
    val valor: BigDecimal,

    @field:Min(1)
    @field:Max(48)
    val parcelas: Int,

    @field:NotNull
    @field:Future
    val primeiraParcela: LocalDate
) {
    fun toModel(c: Cliente): Credito = Credito(
        valor = this.valor,
        parcelas = this.parcelas,
        primeiraParcela = this.primeiraParcela,
        cliente = c
    )
}