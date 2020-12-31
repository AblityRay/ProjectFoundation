package com.haier.hailicommontlib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void StringTest(String a){
        String s1="hello world";
        String s2=new String("hello world");
        String s3="Hello"+" world";
        System.out.println(""+(s1==s2)+"  "+(s2==s3)+" "+(s1==s3));
    }

    public void StringTest(int a){
        String s1="hello world";
        String s2=new String("hello world");
        String s3="Hello"+" world";
        System.out.println(""+(s1==s2)+"  "+(s2==s3)+" "+(s1==s3));
    }
}