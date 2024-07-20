package com.leaderli.demo;

import io.leaderli.litool.core.io.StringReader;

import java.io.InputStream;

public class Request {
    private final InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;

    }

    public void parse() {
        String request = new StringReader(input ).get();
        uri = parseUri(request);

    }

    private String parseUri(String requestString) {
        System.out.println(requestString);

        String[] split = requestString.split(" ", 3);
        if (split.length == 3) {
            return split[1];
        }
        return null;
    }

    public String getUri() {
        return uri;
    }
}
