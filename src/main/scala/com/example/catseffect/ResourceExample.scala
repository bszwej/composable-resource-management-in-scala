package com.example.catseffect

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.example.{DynamoDB, Sqs}

object ResourceExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    program.use(_ => IO.unit).map(_ => ExitCode.Success)

  def program: Resource[IO, Unit] =
    for {
      sqs      <- Resource.fromAutoCloseable(IO(new Sqs))
      dynamoDB <- Resource.fromAutoCloseable(IO(new DynamoDB))
      _        <- Resource.liftF(Service.businessLogic(dynamoDB, sqs))
    } yield ()

}
