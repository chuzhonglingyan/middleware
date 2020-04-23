package com.yuntian.redis;

import java.util.Stack;

/**
 * @Auther: yuntian
 * @Date: 2019/11/20 0020 22:23
 * @Description:
 */
public class TestStack {

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        for (int i = 0; i < 10; i++) {
            s.push(i);
        }
        //集合遍历方式

        for (Integer x : s) {
            System.out.println(x);
        }
        System.out.println("------1-----");
        //栈弹出遍历方式

//                while (s.peek()!=null) {     //不健壮的判断方式，容易抛异常，正确写法是下面的
        while (!s.empty()) {

            System.out.println(s.pop());

        }
        System.out.println("------2-----");
        //错误的遍历方式

//                for (Integer x : s) {

//                        System.out.println(s.pop());

//                }

    }
}
