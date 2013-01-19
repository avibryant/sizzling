package io.sizzling;

public class Aggregation<C> {
  Aggregator<?,C> aggregator;
  C value;

  public Aggregation(Aggregator<?,C> aggregator, C value) {
    this.aggregator = aggregator;
    this.value = value;
  }

  public void include(byte[] bytes) {
    value = aggregator.reduce(value, aggregator.deserialize(bytes));
  }

  public byte[] asBytes() {
    return aggregator.serialize(value);
  }

  public String asString() {
    return aggregator.present(value);
  }
}