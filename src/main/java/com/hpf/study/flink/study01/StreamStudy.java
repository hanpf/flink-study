package com.hpf.study.flink.study01;


import java.util.*;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://juejin.cn/post/6913935489380646925
 */
public class StreamStudy {


    public static void main(String[] args) {

    }


    /**
     * 收集到指定集合中
     */
    void streamCollect(){
        List<String> one = new ArrayList<>();
        Collections.addAll(one,"康熙","雍正","乐视");

         List<String> toList = one.stream().collect(Collectors.toList());
         Set<String> toSet = one.stream().collect(Collectors.toSet());

         ArrayList<String> list = one.stream().collect(Collectors.toCollection(ArrayList::new));

         HashSet<String> hashSet = one.stream().collect(Collectors.toCollection(HashSet::new));

         String[] toArray = one.stream().toArray(String[]::new);

    }

    /**
     * 对流进行聚合计算
     */

    void testStreamToOther(){
         Stream<Person> personStream = Stream.of(
                new Person("吕布", 12),
                new Person("赵云", 14),
                new Person("关羽", 23),
                new Person("张飞", 34)
        );
        final Optional<Person> max = personStream.collect(Collectors.maxBy((s1, s2) -> s1.getAge() - s2.getAge()));
        final Optional<Person> min = personStream.collect(Collectors.minBy((s1, s2) -> s1.getAge() - s2.getAge()));
        final Integer sum = personStream.collect(Collectors.summingInt(s -> s.getAge()));
        final Double avg = personStream.collect(Collectors.averagingInt(s -> s.getAge()));
        final Long count = personStream.collect(Collectors.counting());

    }

    void testStreamGroup(){
        Stream<Person> personStream = Stream.of(
                new Person("吕布", 12),
                new Person("赵云", 14),
                new Person("关羽", 23),
                new Person("张飞", 34)
        );


    }

}
