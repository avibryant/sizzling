package io.sizzling;

public abstract class Aggregator<V,C> {
  public abstract C prepare(V in);
  public abstract C reduce(C left, C right);
  public abstract String present(C out);
  public abstract byte[] serialize(C temp);
  public abstract C deserialize(byte[] bytes);

  public byte[] prepareAndSerialize(V in) {
    return serialize(prepare(in));
  }

  public Aggregation<C> aggregation(byte[] bytes) {
    return new Aggregation<C>(this, deserialize(bytes));
  } 
}