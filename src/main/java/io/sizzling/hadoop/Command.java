package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.JobContext;

interface Command {
  public void configure(org.apache.hadoop.mapreduce.Job mrJob, Job szJob, String[] args);
}