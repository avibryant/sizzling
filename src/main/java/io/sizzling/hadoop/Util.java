package io.sizzling.hadoop;

import io.sizzling.*;
import org.apache.hadoop.mapreduce.JobContext;

public class Util {
  static public final String JOB_PROPERTY = "io.sizzling.job.classname";

  static <T> Job<T> createJob(JobContext context) {
    try {
      return Job.<T>create(context.getConfiguration().get(JOB_PROPERTY));
    } catch(Exception e) {
      throw new RuntimeException("Could not create Job class: " + e.toString());
    }
  }
}