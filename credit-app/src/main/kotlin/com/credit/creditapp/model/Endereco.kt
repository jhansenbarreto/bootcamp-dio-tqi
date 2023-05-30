package com.credit.creditapp.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Endereco(

    @Column(nullable = false)
    var cep: String,

    @Column(nullable = false)
    var logradouro: String
)
