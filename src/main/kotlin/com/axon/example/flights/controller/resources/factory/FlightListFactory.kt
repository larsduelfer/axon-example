package com.axon.example.flights.controller.resources.factory

import com.axon.example.flights.controller.resources.FlightListResource
import com.axon.example.flights.controller.resources.FlightResource
import com.axon.example.flights.projections.Flight
import org.springframework.stereotype.Component

@Component
class FlightListFactory {

  fun build(flights: List<Flight>): FlightListResource =
      FlightListResource(flights = flights.map(FlightResource::from).toList())
}
