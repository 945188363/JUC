package com.njh.JUCTest.Lambda;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName:LambdaTest
 * @Author:njh
 * @Description:lambda测试
 */
public class LambdaTest {
    public static void main(String[] args) {
        // lambda表达式作为参数
        Animal a = ()-> System.out.println("wawawa");
        a.Crow();

        // Function接口没办法实现多参数或者无返回数据格式的函数式编程，没有lambda+接口效果好。
        // 因此，要做到js中随心所欲的箭头函数和函数式编程是很难做到的，函数仍然不是一等公民。
        // 如果需要多个参数，可以根据柯里化方法层层封装

        // 一个参数：Function<T,R>
        Function<Integer,Integer> function = (i)->{
          i++;
          return i;
        };
        // 两个参数：BiFunction<T,U,R>
        BiFunction<Integer,Integer,Integer> function2 = (x,y)->{
            x++;y++;
            return x+y;
        };
        // 无参数：Supplier<R>
        Supplier<Integer> function3 = ()->{
            return 222;
        };
        // 调用Function只需要调用apply方法即可
        System.out.println(function.apply(21));

        System.out.println(function2.apply(1,2));

        System.out.println(function3.get());

    }
}
