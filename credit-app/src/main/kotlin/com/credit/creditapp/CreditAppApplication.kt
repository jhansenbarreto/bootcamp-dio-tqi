package com.credit.creditapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CreditAppApplication

fun main(args: Array<String>) {
	runApplication<CreditAppApplication>(*args)
}
