package com.credit.creditapp.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun configurationDoc(): OpenAPI =
        OpenAPI().info(informacoesAPI())

    private fun informacoesAPI(): Info =
        Info()
            .title("API de simulação de pedidos de crédito")
            .description("API desenvolvida para fins de estudo em Kotlin com Spring Boot no Bootcamp TQI Kotlin - Backend Developer ofertado pela DIO.")
            .version("1.0")
            .contact(contatoAPI())

    private fun contatoAPI(): Contact =
        Contact()
            .name("Jhansen Barreto")
            .email("jhansen.barreto@ftc.edu.br")
            .url("http://br.linkedin.com/in/jhansen-c-barreto")
}