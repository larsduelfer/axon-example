package com.axon.example.airports.aggregate

import java.util.*
import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Airport(
    @TargetAggregateIdentifier val identifier: UUID,
    var name: String,
    var iataCode: String,
    var city: String
)
