package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestTelnet {

        public static void main(String[] args) throws IOException {

            Socket pingSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                pingSocket = new Socket("192.168.142.128", 22);
                out = new PrintWriter(pingSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            out.println("ping");
            System.out.println(in.readLine());
            out.close();
            in.close();
            pingSocket.close();
        }

        @Test
        public void test() throws IOException {

            InetAddress address = InetAddress.getByName("192.168.142.138");
            boolean reachable = address.isReachable(10000);

            System.out.println("Is host reachable? " + reachable);

        }
}
