package io.sizzling;

public class Aggregation<C> {
  Aggregator<?,C> aggregator;
  C value;

  public Aggregation(Aggregator<?,C> aggregator, C value) {
    this.aggregator = aggregator;
    this.value = value;
  }

  public void aggregate(byte[] bytes) {
    value = aggregator.reduce(value, aggregator.deserialize(bytes));
  }

  public byte[] serialize() {
    return aggregator.serialize(value);
  }
}