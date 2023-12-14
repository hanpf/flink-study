package com.hpf.study.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamingWordCount {
    public static void main(String[] args) throws Exception {

         StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
/*        final DataStreamSource<String> textStream = env.socketTextStream("127.0.0.1", port, "\n");

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
        env.execute("socket stream end!!!");*/

        //flatMap(env);
        keyBy(env);
        env.execute("job end");

    }


    public static void keyBy(){

    }


    public static void flatMap(StreamExecutionEnvironment env){
        env.socketTextStream("localhost",9002).flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                for(String word:s.split(",")){
                    collector.collect(word);
                }
            }
        }).print();

    }

    public static void keyBy(StreamExecutionEnvironment env) throws Exception{


        env.socketTextStream("localhost",9002).slotSharingGroup("source").flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                for(String word:s.split(",")){
                    collector.collect(word);
                }
            }
        }).slotSharingGroup("flatmap").setParallelism(2).map(new MapFunction<String, WordCount>() {
            @Override
            public WordCount map(String s) throws Exception {
                return new WordCount(s,1);
            }
        }).map(new MapFunction<WordCount, WordCount>() {
            @Override
            public WordCount map(WordCount wordCount) throws Exception {
                return new WordCount(wordCount.getWord(),1);
            }
        }).keyBy(new KeySelector<WordCount, String>() {
            @Override
            public String getKey(WordCount wordCount) throws Exception {
                return wordCount.getWord();
            }
        }).sum("count").print();

    }

    public static void keyBy1(StreamExecutionEnvironment env){

        env.socketTextStream("localhost",9002).flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {

            }
        });

    }
}
