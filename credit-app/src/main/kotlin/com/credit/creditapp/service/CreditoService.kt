package com.credit.creditapp.service

import com.credit.creditapp.exception.EntidadeNaoEncontradaException
import com.credit.creditapp.exception.RegraNegocioException
import com.credit.creditapp.model.Credito
import com.credit.creditapp.repository.CreditoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class CreditoService(
    private val repository: CreditoRepository
) {
    @Transactional
    fun salvar(credito: Credito): Credito {
        val dataAtual = LocalDate.now().plusDays(91)

        return if (dataAtual.isBefore(credito.primeiraParcela)) repository.save(credito)
        else throw RegraNegocioException("Data inválida. A data limite é até 90 dias à mais.")
    }

    fun buscarPorCodigo(codigo: UUID, clienteId: Long): Credito =
        repository.findByCodigoAndClienteId(codigo, clienteId).orElseThrow {
            throw EntidadeNaoEncontradaException("ID do cliente e/ou código do crédito incorretos.")
        }
}