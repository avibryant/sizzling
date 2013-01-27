package io.sizzling.examples

import io.sizzling._

class AverageJob extends Job[String] {
  val avg = table(Average, "avg")

  def process(line : String) {
    avg.emit(line.take(1), Integer.parseInt(line))
  }
}