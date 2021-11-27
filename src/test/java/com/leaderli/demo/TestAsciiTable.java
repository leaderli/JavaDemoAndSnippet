package com.leaderli.demo;

import de.vandermeer.asciitable.AsciiTable;
import org.junit.Test;

public class TestAsciiTable {
    @Test
    public void test() {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("row 1 col 1", "row 1 col 2");
        at.addRule();
        at.addRow("row 2 col 1", "row 2 col 2");
        at.addRule();
        String rend = at.render();
        System.out.println(rend);

    }
}
