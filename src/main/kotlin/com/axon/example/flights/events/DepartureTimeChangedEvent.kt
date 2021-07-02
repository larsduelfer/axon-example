package com.axon.example.flights.events

import java.time.Instant
import java.util.*

data class DepartureTimeChangedEvent(val identifier: UUID, val newDepartureTime: Instant)
