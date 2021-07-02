package com.axon.example.flights.projections

import com.axon.example.flights.aggregate.FlightStatusEnum.*
import com.axon.example.flights.events.*
import com.axon.example.flights.queries.AllFlightsQuery
import com.axon.example.flights.queries.FlightByIdentifierQuery
import java.util.*
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class FlightProjection {
  private var flights: MutableMap<UUID, Flight> = mutableMapOf()

  @QueryHandler fun handle(query: FlightByIdentifierQuery): Flight? = flights[query.identifier]

  @QueryHandler fun handle(query: AllFlightsQuery): List<Flight> = flights.values.toList()

  @EventHandler
  fun on(event: FlightScheduledEvent) {
    flights[event.identifier] =
        Flight(
            identifier = event.identifier,
            currentDepartureTime = event.departureTime,
            currentArrivalTime = event.arrivalTime,
            status = PLANNED)
  }

  @EventHandler
  fun on(event: FlightCancelledEvent) {
    flights[event.identifier] = flights[event.identifier]!!.copy(status = CANCELLED)
  }

  @EventHandler
  fun on(event: DepartureTimeChangedEvent) {
    flights[event.identifier] =
        flights[event.identifier]!!.copy(currentDepartureTime = event.newDepartureTime)
  }

  @EventHandler
  fun on(event: ArrivalTimeChangedEvent) {
    flights[event.identifier] =
        flights[event.identifier]!!.copy(currentArrivalTime = event.newArrivalTime)
  }

  @EventHandler
  fun on(event: FlightDelayedEvent) {
    flights[event.identifier] =
        flights[event.identifier]!!.copy(currentArrivalTime = event.newArrivalTime)
  }

  @EventHandler
  fun on(event: FlightDepartedEvent) {
    flights[event.identifier] = flights[event.identifier]!!.copy(status = DEPARTED)
  }

  @EventHandler
  fun on(event: FlightArrivedEvent) {
    flights[event.identifier] = flights[event.identifier]!!.copy(status = ARRIVED)
  }
}
