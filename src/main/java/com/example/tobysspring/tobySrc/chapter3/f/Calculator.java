package com.example.tobysspring.tobySrc.chapter3.f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        BufferedReaderCallback sumCallback =
                br -> {
                    Integer sum = 0;
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sum += Integer.parseInt(line);
                    }
                    return sum;
                };
        return fileReadTemplate(filepath, sumCallback);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        BufferedReaderCallback multiplyCallback =
                br -> {
                    Integer multiply = 1;
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        multiply *= Integer.parseInt(line);
                    }
                    return multiply;
                };
        return fileReadTemplate(filepath, multiplyCallback);
    }

    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            int ret = callback.doSomethingWithReader(br);
            return ret;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
