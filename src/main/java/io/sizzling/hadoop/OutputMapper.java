package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;
import java.io.*;

public class OutputMapper extends Mapper<Text, BytesWritable, Text, Text> {
    private Job job;
    private Text keyWritable = new Text();
    private Text valueWritable = new Text();

    protected void setup(Context context) throws IOException, InterruptedException {
        job = Tool.createJob(context);
    }

    public void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
        String fullKey = key.toString();
        int tableIndex = Emitted.getTableIndex(fullKey);
        String tableKey = Emitted.getTableKey(fullKey);
        Table table = job.getTable(tableIndex);
        byte[] bytes = new byte[value.getSize()];
        System.arraycopy(value.getBytes(), 0, bytes, 0, bytes.length);
        Aggregation agg = table.aggregation(bytes);
        valueWritable.set(agg.asString());
        keyWritable.set(table.getName() + "\t" + tableKey);
        context.write(keyWritable, valueWritable);
    }
}
