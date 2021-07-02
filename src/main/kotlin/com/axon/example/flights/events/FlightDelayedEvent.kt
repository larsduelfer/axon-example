package com.axon.example.flights.events

import java.time.Instant
import java.util.*

data class FlightDelayedEvent(val identifier: UUID, val newArrivalTime: Instant)
