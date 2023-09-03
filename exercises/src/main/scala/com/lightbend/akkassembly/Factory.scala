package com.lightbend.akkassembly

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

class Factory(bodyShop: BodyShop,
              paintShop: PaintShop,
              engineShop: EngineShop,
              wheelShop: WheelShop,
              qualityAssurance: QualityAssurance,
              upgradeShop: UpgradeShop)(implicit system: ActorSystem) {

  def orderCars(quantity: Int): Future[Seq[Car]] = {
    bodyShop.cars
      .via(paintShop.paint)
      .via(engineShop.installEngine)
      .via(wheelShop.installWheels)
      .via(upgradeShop.installUpgrades)
      .via(qualityAssurance.inspect)
      .take(quantity)
      .runWith(Sink.seq)
  }

}
