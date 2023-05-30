package com.credit.creditapp

import com.credit.creditapp.dto.ClienteInput
import com.credit.creditapp.repository.ClienteRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
class ClienteControllerTest {

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/clientes"
    }

    @BeforeEach
    fun inicio() = clienteRepository.deleteAll()

    @AfterEach
    fun fim() = clienteRepository.deleteAll()

    @Test // 201 == CREATED
    fun `deve retornar 201 ao salvar cliente`() {
        val json = objectMapper.writeValueAsString(getClienteInput())
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("68472697070"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test //400 == BAD REQUEST
    fun `deve retornar 400 ao tentar salvar cliente violando regra de negocio`() {
        val json = objectMapper.writeValueAsString(getClienteInput()) //senha menor que 6 caracteres
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test //200 == OK
    fun `deve retornar 200 ao buscar todos os cliente`() {
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    private fun getClienteInput(senha: String = "hahaha"): ClienteInput = ClienteInput(
        primeiroNome = "Bruce",
        ultimoNome = "Wayne",
        cpf = "68472697070", //exemplo do Gerador de CPF -> https://www.4devs.com.br/gerador_de_cpf
        email = "batman@dc.com",
        senha = senha,
        renda = BigDecimal(999999.99),
        cep = "11222000",
        logradouro = "Gotham City"
    )
}