package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.*;
import java.io.*;

public class Combiner extends Reducer<Text, BytesWritable, Text, BytesWritable> {
  private Job job;

  protected void setup(Context context)
    throws IOException, InterruptedException {
      job = Tool.createJob(context);
  }

  public void reduce(Text key, Iterable<BytesWritable> values, Context context) 
    throws IOException, InterruptedException {
      
      Aggregation aggregation = null;

      for (BytesWritable val : values) {
        if(aggregation == null) {
          int tableIndex = Emitted.getTableIndex(key.toString());
          aggregation = job.getTable(tableIndex).aggregation(val.getBytes());
        } else {
          aggregation.include(val.getBytes());
        }
      }

      context.write(key, new BytesWritable(aggregation.asBytes()));
  }
}