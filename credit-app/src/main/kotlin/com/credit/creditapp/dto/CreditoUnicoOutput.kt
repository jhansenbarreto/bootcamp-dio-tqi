package com.credit.creditapp.dto

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Credito
import com.credit.creditapp.model.enumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreditoUnicoOutput(

    val codigo: UUID,
    val valor: BigDecimal,
    val primeiraParcela: LocalDate,
    val parcelas: Int,
    val status: Status,
    val emailCliente: String,
    val rendaCliente: BigDecimal

) {
    constructor(credito: Credito) : this(
        codigo = credito.codigo,
        valor = credito.valor,
        primeiraParcela = credito.primeiraParcela,
        parcelas = credito.parcelas,
        status = credito.status,
        emailCliente = credito.cliente.email,
        rendaCliente = credito.cliente.renda
    )
}
