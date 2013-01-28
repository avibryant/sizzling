szl _all_ the ings
---

This is a Java (and Scala) library for computing aggregates  loosely inspired by Google's [Sawzall](http://research.google.com/archive/sawzall.html). The goal is to achieve a similar feel to the simple Ruby/Python/Perl script you might write for the same task if you were dealing with a small text file on a single machine, but have it work in distributed and/or streaming environments. (Currently, only the Hadoop MapReduce environment is supported).

Building and running a trivial example:

	sbt compile
	./szlng AverageJob data/10k

See the code for this example [here](https://github.com/avibryant/sizzling/blob/master/src/main/scala/io/sizzling/examples/AverageJob.scala).

A quick summary of the Sizzling programming model:

- Each job takes a single input source. For now, only text is supported, so all jobs must be <code>Job&lt;String></code> or, in Scala, <code>RichJob[String]</code>.

- Each job defines one or more tables. Conceptually, these are like hashtables that keep, for each key, a counter or some other fixed-size summary of a set of values. The summary could be max value, mean value, number of distinct values, heavy hitters, a bloom filter, a linear regression… anything that can be computed, or estimated, by a distributed and online algorithm.

- For each row in the input, the job can add values to the sets of any number of keys of any of the tables defined by the job. These are ultimately all merged together into a single summary value for each key in each table.

- The outputs from multiple runs of the same job can also be merged without needing to re-process their respective inputs.


Sizzling integrates with (but does not require) the [algebird](http://github.com/twitter/algebird) and [chill](https://github.com/twitter/chill) libraries originally developed for Twitter's scalding project; many useful and interesting aggregates have already been implemented there. The code for that integration is [here](https://github.com/avibryant/sizzling/blob/master/src/main/scala/io/sizzling/MonoidAggregator.scala).

Immediate TODO in rough priority order:

- write Aggregators for more of Algebird's monoids.
	
- support Hadoop input formats other than text.

- actually implement the merge job.

- write a much better wrapper script than the current szlng.

- support at least one realtime/streaming system, like Storm or Kafka or something built on Redis.