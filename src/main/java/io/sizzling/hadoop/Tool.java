package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.util.ToolRunner;

import java.util.*;

public class Tool extends Configured implements org.apache.hadoop.util.Tool {
     
  public int run(String[] args) throws Exception {
    Configuration conf = getConf();
   
    String jobClassName = args[0];
    Job szJob = Job.create(jobClassName);
    conf.set(JOB_PROPERTY, jobClassName);

    String commandClassName = args[1];
    Command command = createCommand(commandClassName);
    conf.set(COMMAND_PROPERTY, commandClassName);

    org.apache.hadoop.mapreduce.Job mrJob = new org.apache.hadoop.mapreduce.Job(conf);
    
    command.configure(mrJob, szJob, Arrays.copyOfRange(args, 2, args.length));

    mrJob.setJarByClass(Tool.class);
    mrJob.submit();
    return 0;
  }

  static public final String JOB_PROPERTY = "io.sizzling.job";
  static public final String COMMAND_PROPERTY = "io.sizzling.command";

  static <T> Job<T> createJob(JobContext context) {
    try {
      return Job.<T>create(context.getConfiguration().get(JOB_PROPERTY));
    } catch(Exception e) {
      throw new RuntimeException("Could not create Job: " + e.toString());
    }
  }

  static Command createCommand(JobContext context) {
    return createCommand(context.getConfiguration().get(COMMAND_PROPERTY));
  }

  static Command createCommand(String className) {
    try {
      return (Command) Class.forName(className).newInstance();
    } catch(Exception e) {
      throw new RuntimeException("Could not create Command: " + e.toString());
    }
  }

  public static void main(String[] args) throws Exception {
    ToolRunner.run(new Tool(), args);
  }
}