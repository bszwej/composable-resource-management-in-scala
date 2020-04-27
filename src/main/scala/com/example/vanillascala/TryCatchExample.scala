package com.example.vanillascala

import com.example.HttpServer

object TryCatchExample extends App {
  val resource = new HttpServer {}

  try {
    resource.use
  } finally {
    resource.close()
  }

}
