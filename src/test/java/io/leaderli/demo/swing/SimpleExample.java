package io.leaderli.demo.swing;

import org.junit.jupiter.api.Test;

import javax.swing.*;

public class SimpleExample {

    @Test
    void test() {


        System.out.println(JOptionPane.showInputDialog(null, "Please choose a name", "Example 1",
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                        "Amanda", "Colin", "Don", "Fred", "Gordon", "Janet", "Jay", "Joe",
                        "Judie", "Kerstin", "Lotus", "Maciek", "Mark", "Mike", "Mulhern",
                        "Oliver", "Peter", "Quaxo", "Rita", "Sandro", "Tim", "Will"}, "Joe"));

    }

    @Test
    void test1() {
        System.out.println(JOptionPane.showInputDialog(null, "Please enter your name", "Example 2",
                JOptionPane.QUESTION_MESSAGE, null, null, "Shannon"));
    }
}
