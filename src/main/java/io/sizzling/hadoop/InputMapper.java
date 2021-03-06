package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;
import java.io.*;

public abstract class InputMapper<T,K,V> extends Mapper<K, V, Text, BytesWritable> implements Emitter {
    private Job<T> job;
    private Text keyWritable = new Text();
    private BytesWritable valueWritable = new BytesWritable();
    private Context context;
    private IOException ioException;
    private InterruptedException intException;

    protected void setup(Context context) throws IOException, InterruptedException {
        job = Tool.<T>createJob(context);
        job.setEmitter(this);
    }

    public void map(K key, V value, Context context) throws IOException, InterruptedException {
        this.context = context;
        job.process(process(key, value));
        if(ioException != null) {
            throw ioException;
        }
        if(intException != null) {
            throw intException;
        }
    }

    public void emit(Emitted e) {
        keyWritable.set(e.makeKeyString());
        valueWritable = new BytesWritable(e.valueBytes);
        try {
            context.write(keyWritable, valueWritable);
        } catch(IOException ex) {
            ioException = ex;
        } catch(InterruptedException ex) {
            intException = ex;
        }
    }  

    protected abstract T process(K key, V value);
}
