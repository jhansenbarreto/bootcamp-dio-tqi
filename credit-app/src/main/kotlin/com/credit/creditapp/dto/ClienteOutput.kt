package com.credit.creditapp.dto

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Endereco
import java.math.BigDecimal

data class ClienteOutput(

    val id: Long,
    val primeiroNome: String,
    val ultimoNome: String,
    val cpf: String,
    val email: String,
    val renda: BigDecimal,
    val endereco: Endereco
) {
    constructor(cliente: Cliente) : this(
        id = cliente.id!!,
        primeiroNome = cliente.primeiroNome,
        ultimoNome = cliente.ultimoNome,
        cpf = cliente.cpf,
        email = cliente.email,
        renda = cliente.renda,
        endereco = Endereco(
            cliente.endereco.cep,
            cliente.endereco.logradouro
        )
    )
}
