package com.credit.creditapp.service

import com.credit.creditapp.exception.EntidadeNaoEncontradaException
import com.credit.creditapp.model.Cliente
import com.credit.creditapp.repository.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClienteService(
    private val repository: ClienteRepository
) {
    fun listar(): List<Cliente> = repository.findAll()

    @Transactional
    fun salvar(cliente: Cliente): Cliente = repository.save(cliente)

    fun buscarPoId(id: Long): Cliente = repository.findById(id).orElseThrow {
        throw EntidadeNaoEncontradaException("ID $id inválido.")
    }

    @Transactional
    fun excluir(id: Long) = repository.delete(buscarPoId(id))
}