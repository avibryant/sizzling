package io.sizzling.redis;

import io.sizzling.*;
import redis.clients.jedis.Jedis;
import java.io.*;
import java.util.*;

public class TextRunner {
  Job<String> job;

  public TextRunner(Jedis jedis, String prefix, Job<String> job) {
    this.job = job;
    Loader loader = new Loader(jedis, prefix);
    job.setEmitter(loader);
  }

  public void read(InputStream in) {
    Scanner scanner = new Scanner(in);
    while(scanner.hasNextLine()) {
      job.process(scanner.nextLine());
    }
  }

  public static void main(String[] args)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
   {
    String className = args[0];
    Job<String> job = Job.<String>create(className);
    Jedis jedis = new Jedis("localhost");
    TextRunner runner = new TextRunner(jedis, className, job);
    runner.read(System.in);
  }
}