package io.leaderli.demo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.leaderli.litool.core.type.ReflectUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.lang.reflect.Field;


public class TestJschExec {

    public static void main(String[] args) throws Exception {


        String host = "centos7";
        String username = "li";
        String password = "li";
        int port = 22;

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setTimeout(2000);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();

        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        exec.setCommand("sleep 1000 && ls aaa");
        exec.connect();

//        exec.setInputStream(System.in);
//        exec.setOutputStream(System.out);
        Field connected = Channel.class.getDeclaredField("connected");
        connected.setAccessible(true);
        System.out.println(connected.get(exec));
        System.out.println(ReflectUtil.getFieldValue(exec, "connected").get());
        System.out.println(exec.isConnected());
        connected = Channel.class.getDeclaredField("connected");
        connected.setAccessible(true);
        System.out.println(connected.get(exec));
        PipedInputStream inputStream= (PipedInputStream) exec.getInputStream();
        PipedInputStream errStream = (PipedInputStream) exec.getErrStream();


//        System.out.println(ReflectUtil.getFieldValue(exec, "connected").get());
        print(inputStream);

        print(errStream);

//        System.out.println(ReflectUtil.getFieldValue(exec, "connected").get());


        exec.disconnect();
        session.disconnect();

    }

    private static void print(PipedInputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {

            int read;
            while ((read = reader.read()) != -1) {
                System.out.print((char)read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





