package io.sizzling.redis;

import io.sizzling.*;
import redis.clients.jedis.Jedis;

public class Loader implements Emitter {
  private Jedis jedis;
  private String prefix;

  public Loader(Jedis jedis, String prefix) {
    this.jedis = jedis;
    this.prefix = prefix;
  }

  public void emit(Emitted e) {
    String key = prefix + ":" + e.makeKeyString();
    String value = new String(e.valueBytes);
    jedis.sadd(prefix + "::DIRTY", key);
    jedis.rpush(key, value);
  }
}