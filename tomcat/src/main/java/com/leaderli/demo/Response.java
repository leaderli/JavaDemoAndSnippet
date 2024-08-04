package com.leaderli.demo;

import io.leaderli.litool.core.io.StringReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Locale;

public class Response implements ServletResponse {
    private final OutputStream output;
    private Request request;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;

    }

    public void sendStaticResource() {

        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                String content = new StringReader(Files.newInputStream(file.toPath())).get();
                byte[] bytes = content.getBytes();
                output.write(("HTTP/1.1 200 \r\n" +
                        "Content-Type:text/html\r\n" +
                        "Content-length:" + bytes.length+"\r\n\r\n").getBytes());
                output.write(bytes);
                return;
            }

            //language=HTML
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type:text/html\r\n" +
                    "Content-length:23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCharacterEncoding() {
        return "";
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
