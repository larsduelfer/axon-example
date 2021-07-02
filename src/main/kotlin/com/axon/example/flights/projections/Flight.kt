package com.axon.example.flights.projections

import com.axon.example.flights.aggregate.FlightStatusEnum
import java.time.Instant
import java.util.*

data class Flight(
    val identifier: UUID,
    val currentDepartureTime: Instant,
    val currentArrivalTime: Instant,
    val status: FlightStatusEnum
)
