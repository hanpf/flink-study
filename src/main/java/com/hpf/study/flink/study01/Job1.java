package com.hpf.study.flink.study01;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Job1 {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
                conf.setString(RestOptions.BIND_PORT,"8081-9000");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(
                conf);

        DataStreamSource<String> source = env.socketTextStream("localhost", 9999);

        final SingleOutputStreamOperator<WordCount> sum = source
                .flatMap(new FlatMapFun())
                .keyBy("word")
                .sum("count");

        sum.print();
        env.execute("job1");


    }


   public static class FlatMapFun implements FlatMapFunction<String, WordCount> {

        @Override
        public void flatMap(String value, Collector<WordCount> out) throws Exception {

            final String[] words = value.split(",");

            for (String word : words) {
                out.collect(new WordCount(word, 1));
            }
        }
    }

   public static class WordCount {
        private String word;
        private Integer count;

        public WordCount() {
        }

        public WordCount(String word, Integer count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }
    }
}
