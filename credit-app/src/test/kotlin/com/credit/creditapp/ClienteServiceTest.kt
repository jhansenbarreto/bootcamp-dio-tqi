package com.credit.creditapp

import com.credit.creditapp.exception.EntidadeNaoEncontradaException
import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Endereco
import com.credit.creditapp.repository.ClienteRepository
import com.credit.creditapp.service.ClienteService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class ClienteServiceTest {

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @InjectMockKs
    lateinit var clienteService: ClienteService

    @Test
    fun `deve criar cliente`() {
        val clienteTest = getCliente()

        every { clienteRepository.save(any()) } returns clienteTest

        val retorno = clienteService.salvar(clienteTest)

        Assertions.assertThat(retorno).isNotNull
        Assertions.assertThat(retorno).isSameAs(clienteTest)

        verify(exactly = 1) { clienteRepository.save(clienteTest) }
    }

    @Test
    fun `deve buscar cliente por id`() {
        val idTest = Random.nextLong()
        val clienteTest = getCliente(idTest)

        every { clienteRepository.findById(idTest) } returns Optional.of(clienteTest)

        val retorno = clienteService.buscarPoId(idTest)

        Assertions.assertThat(retorno).isNotNull
        Assertions.assertThat(retorno).isExactlyInstanceOf(Cliente::class.java)
        Assertions.assertThat(retorno).isSameAs(clienteTest)

        verify(exactly = 1) { clienteRepository.findById(idTest) }
    }

    @Test
    fun `deve lancar EntidadeNaoEncontradaException buscando cliente com id invalido`() {
        val idTest = Random.nextLong()

        every { clienteRepository.findById(idTest) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(EntidadeNaoEncontradaException::class.java)
            .isThrownBy { clienteService.buscarPoId(idTest) }
            .withMessage("ID $idTest inválido.")

        verify(exactly = 1) { clienteRepository.findById(idTest) }
    }

    @Test
    fun `deve deletar cliente por id`() {
        val idTest = Random.nextLong()
        val clienteTest = getCliente(idTest)

        every { clienteRepository.findById(idTest) } returns Optional.of(clienteTest)
        every { clienteRepository.delete(clienteTest) } just runs

        clienteService.excluir(idTest)

        verify(exactly = 1) { clienteRepository.findById(idTest) }
        verify(exactly = 1) { clienteRepository.delete(clienteTest) }
    }

    private fun getCliente(idTest: Long? = null): Cliente = Cliente(
        id = idTest,
        primeiroNome = "Bruce",
        ultimoNome = "Wayne",
        cpf = "12345678900",
        email = "batman@dc.com",
        senha = "joker",
        renda = BigDecimal(999999.99),
        endereco = Endereco(
            cep = "11222000",
            logradouro = "Gotham City"
        )
    )
}