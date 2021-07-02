package com.axon.example.flights.controller.resources

import com.axon.example.flights.aggregate.FlightStatusEnum
import com.axon.example.flights.commands.ScheduleFlightCommand
import com.axon.example.flights.projections.Flight
import java.time.Instant
import java.util.*
import org.axonframework.commandhandling.GenericCommandMessage

data class FlightResource(
    val identifier: UUID?,
    val departureTime: Instant,
    val arrivalTime: Instant,
    val status: FlightStatusEnum?
) {
  fun toScheduleFlightCommand(): ScheduleFlightCommand =
      ScheduleFlightCommand(identifier ?: UUID.randomUUID(), departureTime, arrivalTime)

  fun toScheduleFlightCommandMessage() =
      GenericCommandMessage.asCommandMessage<ScheduleFlightCommand>(toScheduleFlightCommand())

  companion object {
    fun from(flight: Flight) =
        FlightResource(
            identifier = flight.identifier,
            departureTime = flight.currentDepartureTime,
            arrivalTime = flight.currentArrivalTime,
            status = flight.status)
  }
}
