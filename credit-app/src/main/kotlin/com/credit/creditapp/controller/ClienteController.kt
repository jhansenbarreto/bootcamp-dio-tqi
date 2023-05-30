package com.credit.creditapp.controller

import com.credit.creditapp.dto.ClienteInput
import com.credit.creditapp.dto.ClienteOutput
import com.credit.creditapp.dto.ClienteUpdate
import com.credit.creditapp.service.ClienteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClienteController(
    private val service: ClienteService
) {
    @GetMapping
    fun listar(): List<ClienteOutput> =
        service.listar().stream().map { ClienteOutput(cliente = it) }.toList()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastrar(@RequestBody @Valid cliente: ClienteInput): ClienteOutput =
        ClienteOutput(service.salvar(cliente.toModel()))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid cliente: ClienteUpdate): ClienteOutput {
        var clienteDB = service.buscarPoId(id);
        clienteDB = service.salvar(cliente.toModel(clienteDB))
        return ClienteOutput(clienteDB)
    }

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ClienteOutput =
        ClienteOutput(cliente = service.buscarPoId(id))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(@PathVariable id: Long) =
        service.excluir(id)
}