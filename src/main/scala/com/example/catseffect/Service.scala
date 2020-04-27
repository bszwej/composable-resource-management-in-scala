package com.example.catseffect

import cats.effect.IO
import com.example.{DynamoDB, Sqs}

object Service {
  def businessLogic(dynamo: DynamoDB, sqs: Sqs): IO[Unit] =
    for {
      _ <- IO(dynamo.use)
      _ <- IO(sqs.use)
    } yield ()
}
