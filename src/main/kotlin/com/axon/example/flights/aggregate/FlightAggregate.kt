package com.axon.example.flights.aggregate

import com.axon.example.exception.PreconditionViolationException
import com.axon.example.flights.aggregate.FlightStatusEnum.*
import com.axon.example.flights.commands.*
import com.axon.example.flights.events.*
import java.time.Instant
import java.util.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class FlightAggregate {

  @AggregateIdentifier private lateinit var identifier: UUID
  lateinit var currentDepartureTime: Instant
  lateinit var currentArrivalTime: Instant
  lateinit var status: FlightStatusEnum
  // @AggregateMember val legs: List<FlightLeg>,
  // @AggregateMember val seatMap: SeatMap

  protected constructor()

  @CommandHandler
  constructor(command: ScheduleFlightCommand) {
    apply(FlightScheduledEvent(command.identifier, command.departureTime, command.arrivalTime))
  }

  @CommandHandler
  fun handle(command: CancelFlightCommand) {
    if (status in arrayOf(DEPARTED, ARRIVED)) {
      throw PreconditionViolationException("Flight can only be cancelled if is it not yet departed")
    } else if (status == PLANNED) {
      apply(FlightCancelledEvent(command.identifier))
    }
  }

  @CommandHandler
  fun handle(command: ChangeDepartureTimeCommand) {
    if (status != PLANNED) {
      throw PreconditionViolationException(
          "Departure time can only be changed as long as flight is just planned.")
    }
    apply(ArrivalTimeChangedEvent(command.identifier, command.newDepartureTime))
  }

  @CommandHandler
  fun handle(command: ChangeArrivalTimeCommand) {
    if (status in arrayOf(CANCELLED, ARRIVED)) {
      throw PreconditionViolationException(
          "Arrival time can only be changed if flight is planned or has not yet arrived")
    }
    if (currentArrivalTime.isAfter(command.newArrivalTime)) {
      apply(DepartureTimeChangedEvent(command.identifier, command.newArrivalTime))
    } else {
      apply(FlightDelayedEvent(command.identifier, command.newArrivalTime))
    }
  }

  @CommandHandler
  fun handle(command: RegisterDepartureCommand) {
    if (status in arrayOf(DEPARTED, ARRIVED)) {
      // do nothing
    } else if (status != PLANNED) {
      throw PreconditionViolationException(
          "Flight is already Arrival time can only be changed if flight is planned or has not yet arrived")
    }
    apply(FlightDepartedEvent(command.identifier))
  }

  @CommandHandler
  fun handle(command: RegisterArrivalCommand) {
    if (status == ARRIVED) {
      // do nothing
    } else if (status == CANCELLED) {
      throw PreconditionViolationException("Flight was already cancelled.")
    }
    apply(FlightArrivedEvent(command.identifier))
  }

  @EventSourcingHandler
  fun on(event: FlightScheduledEvent) {
    this.identifier = event.identifier
    this.currentDepartureTime = event.departureTime
    this.currentArrivalTime = event.arrivalTime
    this.status = PLANNED
  }

  @EventSourcingHandler
  fun on(event: FlightCancelledEvent) {
    this.status = CANCELLED
  }

  @EventSourcingHandler
  fun on(event: DepartureTimeChangedEvent) {
    this.currentDepartureTime = event.newDepartureTime
  }

  @EventSourcingHandler
  fun on(event: ArrivalTimeChangedEvent) {
    this.currentArrivalTime = event.newArrivalTime
  }

  @EventSourcingHandler
  fun on(event: FlightDelayedEvent) {
    this.currentArrivalTime = event.newArrivalTime
  }

  @EventSourcingHandler
  fun on(event: FlightDepartedEvent) {
    this.status = DEPARTED
  }

  @EventSourcingHandler
  fun on(event: FlightArrivedEvent) {
    this.status = ARRIVED
  }
}

// class SeatMap()
//
// class FlightLeg(
//    @EntityId val legIdentifier: String,
//    val departureAirport: String,
//    val arrivalAirport: String
// )
