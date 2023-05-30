package com.credit.creditapp.dto

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Endereco
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class ClienteInput(

    @field:NotBlank
    val primeiroNome: String,

    @field:NotBlank
    val ultimoNome: String,

    @field:CPF
    @field:NotBlank
    val cpf: String,

    @Email
    @field:NotBlank
    val email: String,

    @field:Size(min = 6)
    val senha: String,

    @field:NotNull
    @field:PositiveOrZero
    val renda: BigDecimal,

    @field:NotBlank
    @field:Pattern(regexp = "[0-9]{8}")
    val cep: String,

    @field:NotBlank
    val logradouro: String
) {
    fun toModel(): Cliente = Cliente(
        primeiroNome = this.primeiroNome,
        ultimoNome = this.ultimoNome,
        cpf = this.cpf,
        email = this.email,
        senha = this.senha,
        renda = this.renda,
        endereco = Endereco(
            cep = this.cep,
            logradouro = this.logradouro
        )
    )
}
