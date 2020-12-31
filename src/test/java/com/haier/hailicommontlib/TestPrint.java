package com.haier.hailicommontlib;

import org.junit.Test;

/**
 * @author： jjf
 * @date： 2020/9/27
 * @describe：
 */
public class TestPrint {

    public static class A {
        public void some(A a) {
            System.out.println("A");
        }
    }

    public static class B extends A {

        public void some(A a) {
            System.out.println("B");
        }
    }


    @Test
    public void test() {
        A a = new A();
        A b = new B();
        a.some(a);
        b.some(a);
    }

}
