package com.axon.example.flights.commands

import java.util.*
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.modelling.command.TargetAggregateIdentifier

class CancelFlightCommand(@TargetAggregateIdentifier val identifier: UUID) {
  fun toMessage() = GenericCommandMessage.asCommandMessage<CancelFlightCommand>(this)
}
