import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {


    @Test
    public void test1() {
        List<String> list = Arrays.asList("21", "2222", "23323");
        System.out.println(filterList(list, (String s) -> s.length() > 3));

    }


    @Test
    public void test2() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formartDateTime = localDateTime.format(dateTimeFormatter);

        System.out.println(formartDateTime);
        System.out.println(localTime.toString());
        System.out.println(localDate.toString());
    }


    private List<String> filterList(List<String> list, Predicate<String> p) {

        List<String> subList = new ArrayList<>();

        for (String s : list) {
            if (p.test(s)) {
                subList.add(s);
            }
        }

        return subList;
    }



    @Test
    public void testRunnable(){

        Runnable t1 =()->{ System.out.println("我是一个线程");};//使用lambda表达式

        Runnable t2 = new Runnable() {    //使用匿名类
            @Override
            public void run() {
                System.out.println("我是一个线程");
            }
        };


        processThread(t1);
        processThread(t2);
        processThread(()-> System.out.println("我是一个线程"));


    }

    private void processThread(Runnable runnable){
        runnable.run();
    }

}
