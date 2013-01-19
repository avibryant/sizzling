package io.sizzling.simple;

import io.sizzling.*;
import java.io.*;
import java.util.*;

public class TextRunner {
  Job<String> job;
  Collector<String> collector;

  public TextRunner(Job<String> job) {
    this.job = job;
    collector = new Collector(job);
  }

  public void read(InputStream in) {
    Scanner scanner = new Scanner(in);
    while(scanner.hasNextLine()) {
      job.process(scanner.nextLine());
    }
  }

  public void print(PrintStream out) {
    for(Map.Entry<String,Map<String,Aggregation>> tableEntry : collector.getOutput().entrySet()) {
      String table = tableEntry.getKey();
      for(Map.Entry<String,Aggregation> aggEntry : tableEntry.getValue().entrySet()) {
        String value = aggEntry.getValue().asString();
        out.println(table + "\t" + aggEntry.getKey() + "\t" + value);
      }
    }
  }

  public static void main(String[] args)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
   {
    String className = args[0];
    Job<String> job = Job.<String>create(className);
    TextRunner runner = new TextRunner(job);
    runner.read(System.in);
    runner.print(System.out);
  }
}