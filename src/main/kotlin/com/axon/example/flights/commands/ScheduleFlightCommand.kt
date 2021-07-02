package com.axon.example.flights.commands

import java.time.Instant
import java.util.*
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ScheduleFlightCommand(
    @TargetAggregateIdentifier val identifier: UUID,
    val departureTime: Instant,
    val arrivalTime: Instant
)
