package com.credit.creditapp.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.math.BigDecimal

@Entity
data class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var primeiroNome: String,

    @Column(nullable = false)
    var ultimoNome: String,

    @Column(nullable = false, unique = true)
    val cpf: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var senha: String,

    @Column(nullable = false)
    var renda: BigDecimal,

    @Embedded
    var endereco: Endereco,

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "cliente")
    var creditos: List<Credito> = mutableListOf()
)