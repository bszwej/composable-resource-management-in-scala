package com.example.vanillascala

import com.example.{DynamoDB, HttpServer}

object Loaner extends App {

  def withDB[T](handler: DynamoDB => T): T = {
    println("Opening DB")
    val resource = new DynamoDB {}
    try {
      handler(resource)
    } finally {
      resource.close()
    }
  }

  def withHttpServer[T](handler: HttpServer => T): T = {
    println("Opening Http server")
    val resource = new HttpServer {}
    try {
      handler(resource)
    } finally {
      resource.close()
    }
  }

  withDB { db =>
    withHttpServer { httpServer =>
      println("==> Using DB and server")
    }
  }

}

object GeneralizedLoaner extends App {

  def withResource[R, T](resource: => R)(handler: R => T)(close: R => Unit): T =
    try {
      handler(resource)
    } finally {
      close(resource)
    }

  withResource(new DynamoDB {}) { db =>
    withResource(new HttpServer {}) { httpServer =>
      println("==> Using DB and server")
    }(_.close())
  }(_.close())

}
