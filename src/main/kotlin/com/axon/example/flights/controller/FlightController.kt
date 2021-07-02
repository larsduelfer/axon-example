package com.axon.example.flights.controller

import com.axon.example.flights.commands.CancelFlightCommand
import com.axon.example.flights.controller.resources.FlightListResource
import com.axon.example.flights.controller.resources.FlightResource
import com.axon.example.flights.controller.resources.factory.FlightListFactory
import com.axon.example.flights.queries.FlightQueryService
import java.util.*
import java.util.concurrent.CompletableFuture
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FlightController(
    val flightListFactory: FlightListFactory,
    val commandGateway: CommandGateway,
    val flightQueryService: FlightQueryService
) {

  @PostMapping("/flights")
  fun scheduleFlight(
      @RequestBody resource: FlightResource
  ): CompletableFuture<ResponseEntity<FlightResource>> =
      commandGateway.send<UUID>(resource.toScheduleFlightCommand()).thenCompose { identifier ->
        flightQueryService.findFlightByIdentifier(identifier).thenApply { flight ->
          ResponseEntity.ok().body(FlightResource.from(flight.get()))
        }
      }

  @GetMapping("/flights")
  fun getAllFlights(): CompletableFuture<ResponseEntity<FlightListResource>> =
      flightQueryService.findAllFlights().thenApply {
        ResponseEntity.ok(flightListFactory.build(it))
      }

  @GetMapping("/flights/{identifier}")
  fun getFlight(@PathVariable identifier: UUID): CompletableFuture<ResponseEntity<FlightResource>> =
      flightQueryService.findFlightByIdentifier(identifier).thenApply {
        if (it.isEmpty) {
          ResponseEntity.notFound().build()
        } else {
          ResponseEntity.ok(FlightResource.from(it.get()))
        }
      }

  @DeleteMapping("/flights/{identifier}")
  fun cancelFlight(
      @PathVariable identifier: UUID
  ): CompletableFuture<ResponseEntity<FlightResource>> =
      commandGateway.send<UUID>(CancelFlightCommand(identifier)).thenCompose { identifier ->
        flightQueryService.findFlightByIdentifier(identifier).thenApply { flight ->
          ResponseEntity.ok().body(FlightResource.from(flight.get()))
        }
      }
}
