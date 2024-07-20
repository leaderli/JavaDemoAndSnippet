package io.leaderli.demo;

import okhttp3.*;
import org.junit.jupiter.api.Test;

public class TestOkHttp {

    @Test
    public void test() throws Throwable{

        OkHttpClient client = new OkHttpClient();
        String json = "{}";
        Request request = new Request.Builder()
                .url("https:www.baidu.com")
                .addHeader("content-type","application.json")
                .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                .build();
        Call call = client.newCall(request);


    }
}
