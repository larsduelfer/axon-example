package com.axon.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlightSchedulesApplication

fun main(args: Array<String>) {
	runApplication<FlightSchedulesApplication>(*args)
}
