package io.sizzling

import com.twitter.algebird._
import com.twitter.bijection._
import com.twitter.chill._

trait ScalaAggregator[V,C] extends Aggregator[V,C] {
  def monoid : Monoid[C]
  def bijection : Bijection[C, Array[Byte]]

  def prepare(in : V) : C
  def present(out : C) : String

  def reduce(left : C, right : C) = monoid.plus(left, right)
  def serialize(temp : C) = bijection(temp)
  def deserialize(bytes : Array[Byte]) = bijection.invert(bytes)
}

trait BufferableAggregator[V,C]
  extends ScalaAggregator[V,C] {
  
  def bufferable : Bufferable[C]
  val bijection = Bufferable.bijectionOf(bufferable)
}

object Average extends BufferableAggregator[Int, AveragedValue] {
  def monoid = AveragedGroup

  def bufferable = Bufferable.build[AveragedValue] { (bb, av) =>
    var nextBb = bb
    nextBb = Bufferable.reallocatingPut(nextBb) { Bufferable.put(_, av.count) }
    nextBb = Bufferable.reallocatingPut(nextBb) { Bufferable.put(_, av.value) }
    nextBb
  } { bb =>
    val c = Bufferable.get[Long](bb)
    val v = Bufferable.get[Double](bb)
    AveragedValue(c,v)
  }

  /*
  This should work but we get a confusing NPE:
  implicit val tupleBij = 
    Bijection.build[AveragedValue, (Long,Double)]{
      av => (av.count, av.value)
    }{
      case (c, v) => AveragedValue(c,v)
    }

  def bufferable = Bufferable.viaBijection[AveragedValue, (Long,Double)]
  */

  def prepare(in : Int) = AveragedValue(in)
  def present(out : AveragedValue) = out.value.toString
}

case class Sum[V](implicit val monoid : Monoid[V], val bufferable : Bufferable[V])
  extends BufferableAggregator[V,V] {
    def prepare(in : V) = in
    def present(out : V) = out.toString
}

trait KryoAggregator[V,C] extends ScalaAggregator[V,C] {
  val bijection = KryoBijection.asInstanceOf[Bijection[C, Array[Byte]]]
}

object DistinctValues extends KryoAggregator[Int, HLL] {
  def monoid = new HyperLogLogMonoid(12)
  def prepare(in : Int) = monoid.create(HyperLogLog.int2Bytes(in))
  def present(out : HLL) = out.estimatedSize.toString
}



