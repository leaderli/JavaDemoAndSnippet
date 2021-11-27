package com.leaderli.demo;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.IOException;
import java.time.Month;

public class TestTerminal {


    public static void main(String[] args) throws IOException {

        TextIO textIO = TextIoFactory.getTextIO();

        String user = textIO.newStringInputReader()
                .withDefaultValue("admin")
                .read("Username");

        String password = textIO.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .read("Password");

        int age = textIO.newIntInputReader()
                .withMinVal(13)
                .read("Age");

        Month month = textIO.newEnumInputReader(Month.class)
                .read("What month were you born in?");

        TextTerminal terminal = textIO.getTextTerminal();
        terminal.printf("\nUser %s is %d years old, was born in %s and has the password %s.\n",
                user, age, month, password);
    }

}
