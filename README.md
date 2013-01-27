szl _all_ the ings

This is a Java (and Scala) library for computing aggregates  loosely inspired by Google's [Sawzall](http://research.google.com/archive/sawzall.html). The goal is to achieve a similar feel to the simple Ruby/Python/Perl script you might write for the same task if you were dealing with a small text file on a single machine, but have it work in distributed and/or streaming environments. (Currently, only the Hadoop MapReduce environment is supported).

Building and running a trivial example:
````
sbt compile
./szlng AverageJob data/10k
````

See the code for this example here:
https://github.com/avibryant/sizzling/blob/master/src/main/scala/io/sizzling/examples/AverageJob.scala

Sizzling integrates with (but does not require) the [algebird](http://github.com/twitter/algebird) and [chill](https://github.com/twitter/chill) libraries originally developed for Twitter's scalding project.

The code for that integration is here:
https://github.com/avibryant/sizzling/blob/master/src/main/scala/io/sizzling/MonoidAggregator.scala

One large TODO is to write Aggregators for (many) more of Algebird's monoids.