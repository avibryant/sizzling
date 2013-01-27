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
        byte[] bytes = new byte[val.getSize()];
        System.arraycopy(val.getBytes(), 0, bytes, 0, bytes.length);
        if(aggregation == null) {
          int tableIndex = Emitted.getTableIndex(key.toString());
          aggregation = job.getTable(tableIndex).aggregation(bytes);
        } else {
          aggregation.include(bytes);
        }
      }

      context.write(key, new BytesWritable(aggregation.asBytes()));
  }
}