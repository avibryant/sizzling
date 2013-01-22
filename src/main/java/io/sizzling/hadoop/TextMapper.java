package io.sizzling.hadoop;

import org.apache.hadoop.io.*;

public class TextMapper extends JobMapper<String, LongWritable, Text> {
  public String process(LongWritable key, Text value) {
    return value.toString();
  }
}