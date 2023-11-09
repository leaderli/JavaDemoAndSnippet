package io.leaderli.demo;

import com.sun.tools.attach.AttachNotSupportedException;
import io.leaderli.litool.core.io.Tailer;
import io.leaderli.litool.core.io.TailerListener;
import io.leaderli.litool.core.util.ThreadUtil;
import org.junit.Test;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.util.Set;

public class TestTail {


    public static void tail(String filePath) {
        try {
            File file = new File(filePath);

            // 创建文件监视器

            // 创建Tail监听器
            TailerListener listener = new TailerListener() {

                public void fileNotFound() {
                    if (!file.exists()) {
                        try {
                            Files.createFile(file.toPath());
                            System.out.println("not found");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void fileRotated() {
                    System.out.println("fileRotated");
                }
            };

            // 创建Tail实例
            Tailer tailer = Tailer.create(file, listener, 100, true);

            ThreadUtil.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, AttachNotSupportedException {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        String filePath = "file2.txt";
        tail(filePath);
    }


    @Test
    public void main1() {
        // 连接到本地的MBeanServer
        MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

        try {
            // 获取所有的RuntimeMXBean ObjectName
            Set<ObjectName> objectNames = mbsc.queryNames(new ObjectName("java.lang:type=Runtime"), null);

            for (ObjectName objectName : objectNames) {

                // 获取第一个RuntimeMXBean的ObjectInstance
                String pid = (String) mbsc.getAttribute(objectName, "Name");

                System.out.println("Process ID: " + pid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
