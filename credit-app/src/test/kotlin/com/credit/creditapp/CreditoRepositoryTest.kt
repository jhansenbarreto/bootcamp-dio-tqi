package com.credit.creditapp

import com.credit.creditapp.model.Cliente
import com.credit.creditapp.model.Credito
import com.credit.creditapp.model.Endereco
import com.credit.creditapp.model.enumeration.Status
import com.credit.creditapp.repository.CreditoRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditoRepositoryTest {

    @Autowired
    lateinit var creditoRepository: CreditoRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var cliente: Cliente
    private lateinit var credito1: Credito
    private lateinit var credito2: Credito

    private var idCliente: Long = 0
    private var codigo1: String = ""
    private var codigo2: String = ""

    @BeforeEach
    fun inicio() {
        cliente = testEntityManager.persist(getCliente())
        credito1 = testEntityManager.persist(getCredito(cliente))
        credito2 = testEntityManager.persist(getCredito(cliente))

        idCliente = cliente.id!!
        codigo1 = credito1.codigo.toString()
        codigo2 = credito2.codigo.toString()
    }

    @Test
    fun `deve buscar credito por codigo e id do cliente`() {
        val creditoTest1 = creditoRepository.findByCodigoAndClienteId(UUID.fromString(codigo1), idCliente).get()
        val creditoTest2 = creditoRepository.findByCodigoAndClienteId(UUID.fromString(codigo2), idCliente).get()

        Assertions.assertThat(creditoTest1).isNotNull
        Assertions.assertThat(creditoTest2).isNotNull

        Assertions.assertThat(creditoTest1).isSameAs(credito1)
        Assertions.assertThat(creditoTest2).isSameAs(credito2)
    }

    private fun getCredito(clienteTest: Cliente): Credito = Credito(
        codigo = UUID.randomUUID(),
        valor = BigDecimal(1000000),
        primeiraParcela = LocalDate.now().plusMonths(2),
        parcelas = 36,
        status = Status.EM_PROGRESSO,
        cliente = clienteTest
    )

    private fun getCliente(): Cliente = Cliente(
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