package com.nvbac.akka.tcp.socket

import akka.actor.Actor
import akka.io.Tcp.{PeerClosed, Received, Write}
import org.slf4j.LoggerFactory

class SimplisticHandler extends Actor {

  private val logger = LoggerFactory.getLogger(classOf[SimplisticHandler])

  override def receive: Receive = {
    case Received(data) =>
      logger.debug("Received data: {}", data)
      sender() ! Write(data)
    case PeerClosed =>
      logger.debug("Peer close connection has been triggered.")
      context.stop(self)
  }
}
