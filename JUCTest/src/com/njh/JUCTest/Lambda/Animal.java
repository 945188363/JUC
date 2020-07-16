package com.njh.JUCTest.Lambda;


/*
 * 接口类不需要加@FunctionalInterface注解，只需要确保接口中只有一个方法即可
 * @FunctionalInterface注解只是方便在编译时保证只有一个方法并且让开发人员注意到是lambda表达式。
 * 多个方法使用@FunctionalInterface注解在编译时会报错
 **/

@FunctionalInterface
public interface Animal {
    public void Crow();
}
