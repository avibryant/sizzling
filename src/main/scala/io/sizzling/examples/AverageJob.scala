package io.sizzling.examples

import io.sizzling._

//expects an input file with one integer per line
//produces the average value for each initial digit
class AverageJob extends RichJob[String] {
  val avg = table(Average, "avg")

  for(line <- input) {
    val firstDigit = line.take(1)
    val value = Integer.parseInt(line)
    avg(firstDigit) += value
  }
}