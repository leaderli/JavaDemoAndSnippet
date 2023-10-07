package io.leaderli.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemInTest {

    public static void main(String[] args) {
        List<String> tokens = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            tokens.add(scanner.next());
        }
        System.out.println(tokens.size());

        tokens.forEach(System.out::println);
        scanner.close();
    }
}
