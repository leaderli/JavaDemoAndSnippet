package io.leaderli.demo.li_reactor;

import org.codehaus.plexus.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * @author leaderli
 * @since 2022/6/20
 */
public class FluxTest2 {


    @Test
    void test1() {

        RestTemplate restTemplate = null;

        String username = "li";
        String password = "li";
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Map body = restTemplate.exchange("http://example", HttpMethod.GET, entity, Map.class).getBody();
    }

    @Test
    public void test() {

//        reactor.core.publisher.Flux.empty().error(new RuntimeException()).subscribe(System.out::println);
        reactor.core.publisher.Flux.empty().subscribe(System.out::println);
        Iterable<Integer> integers = new Iterable<Integer>() {

            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Integer next() {
                        return i++;
                    }
                };
            }
        };
        Flux.range(1, 10);
        for (Integer in : Flux.fromIterable(integers).toIterable()) {
            System.out.println(in);
        }


    }
}
