package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.io.*;

public class TextInputMapper extends InputMapper<String, LongWritable, Text> {
  public String process(LongWritable key, Text value) {
    return value.toString();
  }
}