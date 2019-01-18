package com.nvbac.akka.tcp.socket

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import org.slf4j.LoggerFactory

class TcpServer extends Actor {
  import context.system

  private val logger = LoggerFactory.getLogger(classOf[TcpServer])

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9999))

  override def receive: Receive = {
    case b @ Bound(localAddress) =>
      context.parent ! b
      logger.debug("Server started at localhost")

    case CommandFailed(_: Bind) =>
      logger.error("Failed to start server at localhost")
      context.stop(self)

    case c @ Connected(remote, local) =>
      logger.info("New connection: " + local + " -> " + remote)
      val handler = context.actorOf(Props[SimplisticHandler])
      val connection = sender()
      connection ! Register(handler)
  }
}
