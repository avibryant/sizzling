package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.io.*;

public class OutputCommand implements Command {
    public void configure(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      mrJob.setMapperClass(OutputMapper.class);

      mrJob.setInputFormatClass(SequenceFileInputFormat.class);
//      mrJob.setInputKeyClass(Text.class);
//      mrJob.setInputValueClass(BytesWritable.class);    

      mrJob.setOutputKeyClass(Text.class);
      mrJob.setOutputValueClass(Text.class);
      mrJob.setOutputFormatClass(TextOutputFormat.class);

      try {
        FileInputFormat.setInputPaths(mrJob, new Path(args[0]));
      } catch(java.io.IOException e) {
        throw new RuntimeException("Could not use " + args[0] + " for input path: " + e);
      }

      FileOutputFormat.setOutputPath(mrJob, new Path(args[1]));
    }
}