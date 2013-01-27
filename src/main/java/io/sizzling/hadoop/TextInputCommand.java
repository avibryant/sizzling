package io.sizzling.hadoop;

import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class TextInputCommand extends InputCommand {
  protected Class getInputFormatClass() {
    return TextInputFormat.class;
  }

  protected Class getMapperClass() {
    return TextInputMapper.class;
  }
}