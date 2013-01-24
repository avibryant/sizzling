package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.io.*;

public abstract class InputCommand<T,K,V> implements Command {
    public void configure(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      mrJob.setMapperClass(InputMapper.class);
      mrJob.setCombinerClass(Combiner.class);
      mrJob.setReducerClass(Combiner.class);
      mrJob.setMapOutputKeyClass(Text.class);
      mrJob.setMapOutputValueClass(BytesWritable.class);
      mrJob.setOutputKeyClass(Text.class);
      mrJob.setOutputValueClass(BytesWritable.class);

      setInputPath(mrJob, szJob, args);
      setInputFormat(mrJob, szJob, args);
      setOutputPath(mrJob, szJob, args);
      setOutputFormat(mrJob, szJob, args);
    }

    protected void setInputPath(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      try {
        FileInputFormat.setInputPaths(mrJob, new Path(args[0]));
      } catch(java.io.IOException e) {
        throw new RuntimeException("Could not use " + args[0] + " for input path: " + e);
      }
    }

    protected void setOutputPath(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      FileOutputFormat.setOutputPath(mrJob, new Path(args[1]));
    }

    protected void setOutputFormat(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      mrJob.setOutputFormatClass(SequenceFileOutputFormat.class);
    }

    protected abstract void setInputFormat(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args);
    public abstract T process(K key, V value);
}