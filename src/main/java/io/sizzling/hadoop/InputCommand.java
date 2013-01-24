package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.io.*;

public abstract class InputCommand implements Command {
    public void configure(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      mrJob.setMapperClass(getMapperClass());
      mrJob.setInputFormatClass(getInputFormatClass());

      mrJob.setCombinerClass(Combiner.class);
      mrJob.setReducerClass(Combiner.class);
      mrJob.setMapOutputKeyClass(Text.class);
      mrJob.setMapOutputValueClass(BytesWritable.class);
      mrJob.setOutputKeyClass(Text.class);
      mrJob.setOutputValueClass(BytesWritable.class);      
      mrJob.setOutputFormatClass(SequenceFileOutputFormat.class);

      try {
        FileInputFormat.setInputPaths(mrJob, new Path(args[0]));
      } catch(java.io.IOException e) {
        throw new RuntimeException("Could not use " + args[0] + " for input path: " + e);
      }

      FileOutputFormat.setOutputPath(mrJob, new Path(args[1]));
    }

    protected abstract Class getMapperClass();
    protected abstract Class getInputFormatClass();
}