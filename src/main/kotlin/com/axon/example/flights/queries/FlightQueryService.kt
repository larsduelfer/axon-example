package com.axon.example.flights.queries

import com.axon.example.flights.projections.Flight
import java.util.*
import java.util.concurrent.CompletableFuture
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.QueryMessage
import org.springframework.stereotype.Service

@Service
class FlightQueryService(val queryBus: QueryBus) {

  fun findFlightByIdentifier(identifier: UUID): CompletableFuture<Optional<Flight>> {
    val queryMessage: QueryMessage<FlightByIdentifierQuery, Optional<Flight>> =
        GenericQueryMessage(
            FlightByIdentifierQuery(identifier),
            ResponseTypes.optionalInstanceOf(Flight::class.java))
    return queryBus.query(queryMessage).thenApply {
      if (it.isExceptional) {
        throw IllegalStateException()
      } else it.payload
    }
  }

  fun findAllFlights(): CompletableFuture<List<Flight>> {
    val queryMessage: QueryMessage<AllFlightsQuery, List<Flight>> =
        GenericQueryMessage(
            AllFlightsQuery(), ResponseTypes.multipleInstancesOf(Flight::class.java))
    return queryBus.query(queryMessage).thenApply {
      if (it.isExceptional) {
        throw IllegalStateException()
      } else {
        it.payload
      }
    }
  }
}
