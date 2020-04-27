package com.example.vanillascala

import com.example.{DynamoDB, HttpServer, Sqs}

object ResourceExample extends App {
  val httpServerResource = Resource.make(new HttpServer {})(_.close())
  val dbResource         = Resource.make(new DynamoDB         {})(_.close())
  val mqResource         = Resource.make(new Sqs         {})(_.close())

  val resources: Resource[(HttpServer, Sqs, DynamoDB)] =
    for {
      db         <- dbResource
      mq         <- mqResource
      httpServer <- httpServerResource
    } yield (httpServer, mq, db)

  resources.use {
    case (httpServer, mq, db) =>
      println("I'm using httpServer, mq and db")
  }

}
