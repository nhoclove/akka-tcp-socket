package com.nvbac.akka.tcp.socket

import akka.actor.{ActorSystem, Props}

object Server extends App {
  val system = ActorSystem()
  val tcpServer = system.actorOf(Props(classOf[TcpServer]))
}
