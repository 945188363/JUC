package com.njh.JUCTest.Functional;

import java.util.function.Function;

/**
 * @ClassName:FunctionalTest
 * @Author:njh
 * @Description:TODO
 */
public class FunctionalTest {
    /**
     *  函数式编程demo
     *  函数  作为参数传递  类似 nodejs中的  -> function(){}
     **/
    private <R> R testFunctional(Integer param, Function<Integer,R> function){
        return function.apply(param);
    }
    /**
     *  函数实例
     *  作为参数传递
     **/
    private int fun(int i){
        return i+3;
    }
    /**
     *  匿名内部类demo
     *  类  作为参数传递
     **/
    private int testNonameClass(Integer param, TestClass t){
        return t.fun(param);
    }

    /**
     *  匿名内部类demo
     *  类  作为参数传递
     **/
    private int testNonameClass2(Integer param, TestClass2 t){
        return t.fun(param);
    }

    private void test1(){
        //函数编程 测试
        int result = testFunctional(2, i -> fun(i));

        int result4 = testFunctional(2, this::fun);

//        int result5 = testFunctional(2, TestClass::fun);

        System.out.println(result);

        System.out.println(result4);

//        System.out.println(result5);

        //匿名内部类 类形式 测试
        int result1 = testNonameClass(2,new TestClass(){
            @Override
            public int fun (int i) {
                return i + 3;
            }
        });

        //匿名内部类 接口形式 测试
        int result2 = testNonameClass2(2,new TestClass2(){
            @Override
            public int fun (int i) {
                return i + 3;
            }
        });

        //匿名内部类 lambda形式 测试
        int result3 = testNonameClass2(2, i -> i + 3);

        System.out.println(result1);

        System.out.println(result2);

        System.out.println(result3);
    }

    public static void main(String[] args) {
        FunctionalTest t = new FunctionalTest();

        t.test1();
    }
}

/**
 *  类
 *  作为参数传递
 **/
class TestClass{

    public int fun (int i) {
        return i + 3;
    }

}

/**
 *  接口
 *  作为参数传递
 **/
@Deprecated
@FunctionalInterface
interface TestClass2{

    public int fun (int i);

}
