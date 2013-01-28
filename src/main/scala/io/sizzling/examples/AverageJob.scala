package io.sizzling.examples

import io.sizzling._

//expects an input file with one integer per line
//- produces the average value for each initial digit
//- also produces the total number of distinct values
class AverageJob extends RichJob[String] {
  val avg = table(Average, "avg")
  val dv = table(DistinctValues, "distinct")
  
  for(line <- input) {
    val firstDigit = line.take(1)
    val value = Integer.parseInt(line)
    avg(firstDigit) += value
    dv += value
  }
}