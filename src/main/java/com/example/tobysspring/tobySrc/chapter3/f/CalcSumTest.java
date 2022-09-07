package com.example.tobysspring.tobySrc.chapter3.f;

import com.example.tobysspring.tobySrc.chapter3.d.DaoFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CalculatorConfig.class)
public class CalcSumTest {
//    @Autowired
    Calculator calculator;
    String numFilepath;

    @BeforeAll
    public void setUp() {
        this.numFilepath = "C:\\Users\\User\\IdeaProjects\\tobysSpring\\src\\main\\java\\com\\example\\tobysspring\\tobySrc\\chapter3\\e\\numbers.txt";
        this.calculator = new Calculator();
    }

    @Test
    public void sumdOfNumbers() throws IOException {
//        String path = this.getClass().getClassLoader().getResource(
////                "/com/example/tobysspring/tobySrc/chapter3/e/CalcSumText.java"
//                "numbers.txt"
//        ).getPath();

        int sum = calculator.calcSum(numFilepath);
        assertEquals(sum, 10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertEquals(calculator.calcMultiply(this.numFilepath), 24);
    }
}
