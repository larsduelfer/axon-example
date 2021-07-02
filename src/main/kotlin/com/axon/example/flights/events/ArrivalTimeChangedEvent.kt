package com.axon.example.flights.events

import java.time.Instant
import java.util.*

data class ArrivalTimeChangedEvent(val identifier: UUID, val newArrivalTime: Instant)
