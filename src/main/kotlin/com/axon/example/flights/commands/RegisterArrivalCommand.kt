package com.axon.example.flights.commands

import java.util.*
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RegisterArrivalCommand(@TargetAggregateIdentifier val identifier: UUID) {
  fun toMessage() = GenericCommandMessage.asCommandMessage<CancelFlightCommand>(this)
}
