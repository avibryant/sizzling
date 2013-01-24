package io.sizzling.hadoop;

import io.sizzling.*;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;
import java.io.*;

public class InputMapper<T,K,V> extends Mapper<K, V, Text, BytesWritable> implements Emitter {
    private Job<T> job;
    private InputCommand<T,K,V> command;
    private Text keyWritable = new Text();
    private BytesWritable valueWritable = new BytesWritable();
    private Context context;
    private IOException ioException;
    private InterruptedException intException;

    protected void setup(Context context) throws IOException, InterruptedException {
        command = (InputCommand<T,K,V>) Tool.createCommand(context);
        job = Tool.<T>createJob(context);
        job.setEmitter(this);
    }

    public void map(K key, V value, Context context) throws IOException, InterruptedException {
        this.context = context;
        job.process(command.process(key, value));
        if(ioException != null) {
            throw ioException;
        }
        if(intException != null) {
            throw intException;
        }
    }

    public void emit(Emitted e) {
        keyWritable.set(e.makeKeyString());
        valueWritable.set(e.valueBytes, 0, e.valueBytes.length);
        try {
            context.write(keyWritable, valueWritable);
        } catch(IOException ex) {
            ioException = ex;
        } catch(InterruptedException ex) {
            intException = ex;
        }
    }   
}
