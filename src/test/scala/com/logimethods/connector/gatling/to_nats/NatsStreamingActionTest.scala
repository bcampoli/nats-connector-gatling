/*******************************************************************************
 * Copyright (c) 2016 Logimethods
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * http://opensource.org/licenses/MIT
 *******************************************************************************/

package com.logimethods.connector.gatling.to_nats

import akka.actor.{ActorRef, Props}
import io.gatling.core.Predef._
import io.gatling.core.action.builder.ActionBuilder

import scala.concurrent.duration._
import java.util.Properties

class NatsStreamingActionTest extends Simulation {
  
  val clusterID = "test-cluster"
  val natsProtocol = NatsStreamingProtocol(null, clusterID, "TestingSubject")
  
  val natsScn = scenario("NATS call").exec(NatsStreamingBuilder("Hello from Gatling!"))
 
  setUp(
    natsScn.inject(constantUsersPerSec(15) during (1 minute))
  ).protocols(natsProtocol)
}