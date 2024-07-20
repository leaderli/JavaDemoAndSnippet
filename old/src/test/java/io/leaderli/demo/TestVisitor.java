package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

public class TestVisitor {

    private interface Visitor {
        default void visitorDir(File dir) {

        }

        default void visitorFile(File file) {

        }
    }

    public static class FileStruct {

        public final File file;

        public FileStruct(File file) {
            this.file = file;
        }

        public void accept(Visitor visitor) {
            if (file.isDirectory()) {
                visitor.visitorDir(file);
                for (File child : file.listFiles()) {
                    new FileStruct(child).accept(visitor);
                }
            } else {
                visitor.visitorFile(file);
            }
        }
    }


    @Test
    public void test() {

        FileStruct fileStruct = new FileStruct(new File("D:\\download"));

        fileStruct.accept(new Visitor() {

            @Override
            public void visitorFile(File file) {
                if(file.getName().endsWith(".exe")){
                    System.out.println(file.getName());
                }
            }
        });

    }

    @Test
    public void test3() {
        String msg = "hello{0},hello{1}";
        String format = MessageFormat.format(msg, new ArrayList<>(), 100);
        System.out.println("format = " + format);



    }
}
