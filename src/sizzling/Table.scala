package sizzling

import com.twitter.algebird.MonoidAggregator

object Table {
  def apply[A,B,C](aggregator : MonoidAggregator[A,B,C]) = new Table(aggregator)
}

class Table[A,B,C](val aggregator : MonoidAggregator[A,B,C]) {
  class Element {
    var value = aggregator.monoid.zero

    def +=(item : A) {
      value = aggregator.reduce(value, aggregator.prepare(item))
    }
  }

  val map = scala.collection.mutable.HashMap[String,Element]()

  def +=(item : A) { this("") += item } 
  def apply(k : String) = map.getOrElseUpdate(k, new Element)

  def results = map.mapValues{el => aggregator.present(el.value)}.toMap
}