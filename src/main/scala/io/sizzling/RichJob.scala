package io.sizzling

class RichJob[T] extends Job[T] {
  var processFnList : List[T => Unit] = Nil

  val input = new Object {
    def foreach(fn : T => Unit) {
      processFnList ::= fn
    }
  }

  def process(in : T) {
    for(fn <- processFnList)
      fn(in)
  }

  implicit def table2Rich[V](table : Table[V]) = new RichTable(table)
}

class RichTable[V](table : Table[V]) {
  def apply(key : String) = new RichTableElement(table, key)

  def +=(v : V) {
    table.emit("", v)
  }
}

class RichTableElement[V](table : Table[V], key : String) {
  def +=(v : V) {
    table.emit(key, v)
  }
}