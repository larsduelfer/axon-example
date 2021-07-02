package com.axon.example.flights.commands

import java.time.Instant
import java.util.*
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.modelling.command.TargetAggregateIdentifier

class ChangeArrivalTimeCommand(
    @TargetAggregateIdentifier val identifier: UUID,
    val newArrivalTime: Instant
) {
  fun toMessage() = GenericCommandMessage.asCommandMessage<CancelFlightCommand>(this)
}
