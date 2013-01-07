package sizzling

class Tester[T](in : Input[T], seq : Seq[T]) {
  var callbacks : List[() => Unit] = Nil

  def out[A,B,C](t : Table[A,B,C])(fn : Map[String,C] => Unit) {
    callbacks = {() => fn(t.results)} :: callbacks
  }

  def run {
    seq.foreach{in(_)}
    callbacks.foreach{_()}
  }
}