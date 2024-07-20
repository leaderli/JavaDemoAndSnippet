package com.leaderli.demo;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {


    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "tomcat" + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() throws IOException {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));

        while (true) {
            try {

                Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();

                Request request = new Request(input);
                request.parse();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
                if (shutdown) {
                    return;
                }
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
