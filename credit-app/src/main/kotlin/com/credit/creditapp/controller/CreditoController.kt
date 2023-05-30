package com.credit.creditapp.controller

import com.credit.creditapp.dto.CreditoInput
import com.credit.creditapp.dto.CreditoOutput
import com.credit.creditapp.dto.CreditoUnicoOutput
import com.credit.creditapp.service.ClienteService
import com.credit.creditapp.service.CreditoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/creditos/{clienteId}")
class CreditoController(
    private val service: CreditoService,
    private val clienteService: ClienteService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastrar(@PathVariable clienteId: Long, @Valid @RequestBody credito: CreditoInput): CreditoOutput {
        val cliente = clienteService.buscarPoId(clienteId)
        val creditoSalvo = service.salvar(credito.toModel(cliente))
        return CreditoOutput(creditoSalvo)
    }

    @GetMapping
    fun listarCreditosPorCliente(@PathVariable clienteId: Long): List<CreditoOutput> =
        clienteService.buscarPoId(clienteId).creditos.stream().map { CreditoOutput(it) }.toList()

    @GetMapping("/{codigo}")
    fun buscar(@PathVariable clienteId: Long, @PathVariable codigo: UUID): CreditoUnicoOutput {
        val credito = service.buscarPorCodigo(codigo, clienteId)
        return CreditoUnicoOutput(credito)
    }
}