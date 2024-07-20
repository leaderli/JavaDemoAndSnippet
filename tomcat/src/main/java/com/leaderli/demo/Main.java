package com.leaderli.demo;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("debian", 80)) {
            OutputStream os = socket.getOutputStream();
            boolean autoFlush = true;

            PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET / HTTP/1.1");
            out.println("Host: localhost:80");
            out.println("Connection: Close");
            out.println();

            boolean loop = true;

            StringBuilder sb = new StringBuilder(8096);
            while (true) {

                if (in.ready()) {
                    int i;
                    while ((i = in.read()) != -1) {
                        sb.append((char) i);
                    }


                    System.out.println(sb);
                    break;
                }
            }

        }

    }
}
