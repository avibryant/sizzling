package io.sizzling.examples

import io.sizzling._

class AverageJob extends Job[String] {
  val avg = table(Average, "avg")

  def process(line : String) {
    avg.emit("all", Integer.parseInt(line))
  }
}