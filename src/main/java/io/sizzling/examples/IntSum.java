package io.sizzling.aggregator;

import io.sizzling.*;

public class IntSum extends Aggregator<Integer,Integer> {
  public Integer prepare(Integer in) {
    return in;
  }

  public Integer reduce(Integer left, Integer right) {
    return left + right;
  }

  public String present(Integer out) {
    return out.toString();
  }

  public byte[] serialize(Integer temp) {
    return temp.toString().getBytes();
  }

  public Integer deserialize(byte[] bytes) {
    return Integer.decode(new String(bytes));
  }
}