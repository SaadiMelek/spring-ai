package com.mlk.ai.controller;

import com.mlk.ai.SpringAiApplication;
import org.springframework.boot.SpringApplication;

import java.util.Arrays;
import java.util.stream.Stream;

public class Kata {

    public static void main(String[] args) {
        String str1 = "lisTen";
        str1 = str1.replaceAll("\\s+", "").toLowerCase();
        System.out.println(str1);
        String str2 = "Silent";
        str2 = str2.replaceAll("\\s+", "").toLowerCase();
        System.out.println(str2);


        // Conversion en tableaux de caractères et tri
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        Arrays.sort(chars1);
        Arrays.sort(chars2);

        // Comparison des tableaux triés
        System.out.println(Arrays.equals(chars1, chars2));
    }



    public static void palindrome() {
        String str = "A man, a plan, $ a canal, %% Panama";
        str = str.replaceAll("[^A-Za-z0-9]", "");
        System.out.println(str);
        str = str.toLowerCase();
        System.out.println(str);
        String reverseStr = new StringBuilder(str).reverse().toString();
        System.out.println("reverseStr = " + reverseStr);
        System.out.println("Is palindrome = " + (str.equals(reverseStr)));
    }









    public static void fizzBuzz() {
        for(int i = 1; i <= 100; i++) {
            if (i % 5 == 0 && i % 3 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}
