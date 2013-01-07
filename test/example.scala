import sizzling._
import com.twitter.algebird._

val in = Input[Int]
val avg = Table(Averager)

for(i <- in) {
  if(i % 2 == 0)
    avg("even") += i
  else
    avg("odd") += i
}

val tester = new Tester(in, (1 to 100))
tester.out(avg){m => println(m)}
tester.run