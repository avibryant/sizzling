package io.sizzling

import com.twitter.algebird._
import com.twitter.chill._
import com.twitter.bijection._

trait ScalaAggregator[V,C] extends Aggregator[V,C] {
  def monoid : Monoid[C]
  def bijection : Bijection[C, Array[Byte]]

  def prepare(in : V) : C
  def present(out : C) : String

  def reduce(left : C, right : C) = monoid.plus(left, right)
  def serialize(temp : C) = bijection(temp)
  def deserialize(bytes : Array[Byte]) = bijection.invert(bytes)
}

trait KryoAggregator[V,C] extends ScalaAggregator[V,C] {
  val bijection = KryoBijection.asInstanceOf[Bijection[C, Array[Byte]]]
}

object Average extends KryoAggregator[Int, AveragedValue] {
  def monoid = AveragedGroup
  def prepare(in : Int) = AveragedValue(in)
  def present(out : AveragedValue) = out.value.toString
}