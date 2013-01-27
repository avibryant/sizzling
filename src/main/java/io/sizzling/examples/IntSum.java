package io.sizzling.examples;

import io.sizzling.*;
import java.math.BigInteger;

public class IntSum extends Aggregator<Object,BigInteger> {
  public BigInteger prepare(Object in) {
    return new BigInteger(in.toString());
  }

  public BigInteger reduce(BigInteger left, BigInteger right) {
    return left.add(right);
  }

  public String present(BigInteger out) {
    return out.toString();
  }

  public byte[] serialize(BigInteger temp) {
    return temp.toByteArray();
  }

  public BigInteger deserialize(byte[] bytes) {
    return new BigInteger(bytes);
  }
}