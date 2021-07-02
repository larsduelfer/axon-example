package com.axon.example.flights.events

import java.time.Instant
import java.util.*

data class FlightScheduledEvent(
    val identifier: UUID,
    val departureTime: Instant,
    val arrivalTime: Instant
)
