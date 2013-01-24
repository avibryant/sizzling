package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.io.*;

public class TextInputCommand extends InputCommand<String,LongWritable,Text> {
    protected void setInputFormat(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args) {
      mrJob.setInputFormatClass(TextInputFormat.class);
    }

    public String process(LongWritable key, Text value) {
      return value.toString();
    }
}