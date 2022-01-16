package com.hpf.study.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class StreamingWordCount {
    public static void main(String[] args) throws Exception {
        int port;
        try {
            final ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception e) {
            System.out.println("没有指定port，使用默认的port:9000======");
            port = 9000;
        }


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final DataStreamSource<String> textStream = env.socketTextStream("127.0.0.1", port, "\n");

        final SingleOutputStreamOperator<WordCount> word = textStream.flatMap(new FlatMapFunction<String, WordCount>() {

            @Override
            public void flatMap(String s, Collector<WordCount> collector) throws Exception {
                for (String word : s.split("\\s")) {
                    collector.collect(new WordCount(word, 1));
                }
            }
        }).keyBy("word").timeWindow(Time.seconds(5L))
                .reduce(new ReduceFunction<WordCount>() {
                    @Override
                    public WordCount reduce(WordCount a, WordCount b) throws Exception {
                        return new WordCount(a.getWord(), a.getCount() + b.getCount());
                    }
                });
        word.print().setParallelism(1);
        env.execute("socket stream end!!!");

    }
}
