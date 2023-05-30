package com.credit.creditapp.repository

import com.credit.creditapp.model.Credito
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CreditoRepository : JpaRepository<Credito, Long> {

    fun findByCodigoAndClienteId(codigo: UUID, clienteId: Long): Optional<Credito>
}