package com.credit.creditapp.model

import com.credit.creditapp.model.enumeration.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
data class Credito(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val codigo: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val valor: BigDecimal,

    @Column(nullable = false)
    val primeiraParcela: LocalDate,

    @Column(nullable = false)
    val parcelas: Int,

    @Enumerated
    @Column(nullable = false)
    var status: Status = Status.EM_PROGRESSO,

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    val cliente: Cliente
)
