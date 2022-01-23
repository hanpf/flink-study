package com.hpf.study.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class ClickhouseApp {


    public static void main(String[] args) throws Exception{
        //初始化环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //source
        env.socketTextStream("localhost", 9001)
                //业务逻辑转换代码
                .map(new MapFunction<String, Tuple3<Integer,String,Integer>>() {
                    @Override
                    public Tuple3<Integer, String, Integer> map(String s) throws Exception {
                        String[] splits = s.split(",");


                        return Tuple3.of(Integer.valueOf(splits[0].trim()),splits[1].trim(),Integer.valueOf(splits[2].trim()));
                    }

                })
                //sink
                .addSink(JdbcSink.sink(
                        "insert into user1 values(?,?,? )",

                        (pstmt,x)->{
                         pstmt.setInt(1,x.f0);
                         pstmt.setString(2,x.f1);
                         pstmt.setInt(3,x.f2);
                        }
                , JdbcExecutionOptions.builder().withBatchIntervalMs(3000).withBatchSize(3).build(),new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl("jdbc:clickhouse://localhost:8123/db_user")
                                .withDriverName("ru.yandex.clickhouse.ClickHouseDriver").build()
                ));
            //执行应用程序
        env.execute();

    }

}
