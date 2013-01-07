package sizzling

object Input {
  def apply[T] = new Input[T]
}

class Input[T] {
  var ops : List[T => Unit] = Nil

  def foreach(fn : T => Unit) {ops = fn :: ops}
  def filter(fl : T => Boolean) = new Object {
    def foreach(fn : T => Unit) {
      ops = {a : T => if(fl(a)) fn(a)} :: ops
    }
  }

  def apply(a : T) {ops.foreach{op => op(a)}}
}