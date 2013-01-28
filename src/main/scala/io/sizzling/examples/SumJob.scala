package io.sizzling.examples

import io.sizzling._

//expects an input file with one integer per line
//- produces their (integer, probably overflow) sum
//- also produces the (double) sum of their sqrts
class SumJob extends RichJob[String] {
  val sum = table(Sum[Int], "sum")
  val sumSqrt = table(Sum[Double], "sqrt")
  
  for(line <- input) {
    val value = Integer.parseInt(line)
    sum += value
    sumSqrt += Math.sqrt(value)
  }
}