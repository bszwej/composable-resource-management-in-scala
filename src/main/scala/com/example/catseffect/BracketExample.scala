package com.example.catseffect

import cats.effect.{ExitCode, IO, IOApp}
import com.example.{DynamoDB, Sqs}

object BracketExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    IO(new Sqs)
      .bracket { sqs =>
        IO(new DynamoDB)
          .bracket { dynamoDB =>
            Service.businessLogic(dynamoDB, sqs)
          }(client => IO(client.close))
      }(client => IO(client.close))
      .map(_ => ExitCode.Success)

}
