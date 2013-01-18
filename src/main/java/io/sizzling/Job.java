package io.sizzling;

import java.util.*;

abstract public class Job<T> {
  
  private List<Table> tables = new ArrayList<Table>();
  private Emitter emitter;

  public abstract void process(T in);

  protected <V> Table<V> table(Aggregator<V,?> agg, String name) {
    Table<V> t = new Table<V>(agg, name, this, tables.size());
    tables.add(t);
    return t;
  }

  public void setEmitter(Emitter e) {
    this.emitter = e;
  }

  public void emit(int tableIndex, String key, byte[] valueBytes) {
    emitter.emit(new Emitted(tableIndex, key, valueBytes));
  }

  public Table getTable(int index) {
    return tables.get(index);
  }
}