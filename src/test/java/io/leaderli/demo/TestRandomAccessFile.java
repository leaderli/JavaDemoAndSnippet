package io.leaderli.demo;

import io.leaderli.litool.core.util.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.RandomAccessFile;

@SuppressWarnings("resource")
public class TestRandomAccessFile {
    @Test
    void test() throws Exception {
        String name = "1.log";
        File file = new File(name);
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println(file.getAbsolutePath());
        RandomAccessFile rdf = new RandomAccessFile(name, "r");

        byte[] buf = new byte[4096];
        for (int i = 0; i < 1000; i++) {

            rdf.read(buf);
            rdf.seek(rdf.length());
            System.out.println("--->" + read(buf) + "<---");
            buf = new byte[4096];
            ThreadUtil.sleep(3000);
        }

    }

    public String read(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] <= 0) {
                return new String(bytes, 0, i).trim();
            }
        }
        return "";
    }

}
