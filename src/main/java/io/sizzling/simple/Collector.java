package io.sizzling.simple;

import io.sizzling.*;
import java.util.*;

public class Collector<T> implements Emitter {
  Job<T> job;
  Map<String,Map<String,Aggregation>> output = new HashMap<String,Map<String,Aggregation>>();

  public Collector(Job<T> job) {
    this.job = job;
    job.setEmitter(this);
  }

  public void emit(Emitted e) {
    Table table = job.getTable(e.tableIndex);

    Map<String,Aggregation> tableMap = mapForTable(table);
    if(tableMap.containsKey(e.key))
      tableMap.get(e.key).include(e.valueBytes);
    else
      tableMap.put(e.key, table.aggregation(e.valueBytes));
  }

  public Map<String,Map<String,Aggregation>> getOutput() {
    return output;
  }

  private Map<String,Aggregation> mapForTable(Table table) {
    if(output.containsKey(table.getName()))
      return output.get(table.getName());
    else {
      Map<String,Aggregation> tableMap = new HashMap<String,Aggregation>();
      output.put(table.getName(), tableMap);
      return tableMap;
    }
  }
}