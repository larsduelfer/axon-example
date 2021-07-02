package com.axon.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleApplication

fun main(args: Array<String>) {
	runApplication<ExampleApplication>(*args)
}
