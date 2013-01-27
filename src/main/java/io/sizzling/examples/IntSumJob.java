package io.sizzling.examples;

import io.sizzling.*;

public class IntSumJob extends Job<String> {
  Table sum = table(new IntSum(), "sum");

  public void process(String line) {
    int i = Integer.parseInt(line);
    if(i % 2 == 0)
      sum.emit("even", i);
    else
      sum.emit("odd", i);
  }
}