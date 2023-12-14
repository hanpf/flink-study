/*
package com.hpf.study.flink.study01;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceTest {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
       DataStreamSource<String>  stream = env.socketTextStream("localhost",9999);

         env.addSource();
        stream.print();

        env.execute();
    }

}
*/
