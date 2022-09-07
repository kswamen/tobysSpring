package com.example.tobysspring.tobySrc.chapter3.e;

import org.junit.jupiter.api.Test;

import java.io.IOError;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcSumTest {
    @Test
    public void sumdOfNumbers() throws IOException {
        Calculator calculator = new Calculator();

//        String path = this.getClass().getClassLoader().getResource(
////                "/com/example/tobysspring/tobySrc/chapter3/e/CalcSumText.java"
//                "numbers.txt"
//        ).getPath();

        String path = "C:\\Users\\User\\IdeaProjects\\tobysSpring\\src\\main\\java\\com\\example\\tobysspring\\tobySrc\\chapter3\\e\\numbers.txt";
        int sum = calculator.calcSum(path);
        assertEquals(sum, 10);
    }
}
