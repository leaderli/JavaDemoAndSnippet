package io.leaderli.demo;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class TestJsch {

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

        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");

//        channelShell.setInputStream(System.in);
//        channelShell.setOutputStream(System.out);
        InputStream commandOutput = channelShell.getInputStream();

        new Thread(() -> {
            try {
                InputStreamReader reader = new InputStreamReader(commandOutput, StandardCharsets.UTF_8);
                int read = -1;
                while ((read = reader.read()) > -1) {

                    System.out.print((char) read);
                }
            } catch (Exception e) {
            }
        }).start();
        OutputStream commandInput = channelShell.getOutputStream();

        channelShell.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(commandOutput));
        PrintStream writer = new PrintStream(commandInput);

        // 读取欢迎信息等


        // Send commands based on user input
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while ((command = userInputReader.readLine()) != null) {
            sendCommand(writer, command, reader);
        }

        // 关闭连接
        writer.close();
        reader.close();
        channelShell.disconnect();
        session.disconnect();
    }

    private static void sendCommand(PrintStream writer, String command, BufferedReader reader) throws InterruptedException {
        System.out.println("-------->" + command + "<-------------");
        writer.println(command);
        writer.flush();
    }

}





