package io.sizzling;

public class Table<V> {

  private Aggregator<V,?> aggregator;
  private String name;
  private Job job;
  private int index;

  public Table(Aggregator<V,?> aggregator, String name, Job job, int index){
    this.aggregator = aggregator;
    this.name = name;
    this.job = job;
    this.index = index;
  }

  public void emit(V value) {
    emit("", value);
  } 

  public void emit(String key, V value) {
    job.emit(index, key, aggregator.prepareAndSerialize(value));
  }

  public Aggregation aggregation(byte[] bytes) {
    return aggregator.aggregation(bytes);
  }
}