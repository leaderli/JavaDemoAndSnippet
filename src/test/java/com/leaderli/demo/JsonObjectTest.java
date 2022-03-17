package com.leaderli.demo;

import com.google.gson.JsonObject;
import io.leaderli.litil.meta.Lino;
import io.leaderli.litil.meta.Lira;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leaderli
 * @since 2022/3/4 5:35 PM
 */
public class JsonObjectTest {

    @org.junit.jupiter.api.Test
    public void test() throws Throwable {

        List<Object> cardList = new ArrayList<>();
        String cardNo = "123";


        JsonObject result = Lira.of(cardList)
                .filter(item ->
                        Lino.of(item)
                                .cast(JsonObject.class)
                                .map(json -> json.get("carnNbr"))
                                .map(String::valueOf)
                                .filter(cardNo::equals)
                                .isEmpty()
                )
                .cast(JsonObject.class)
                .first().getOrElse(JsonObject::new);


        Lira.of(cardList)
                .filter(item ->
                        Lino.of(item)
                                .cast(JsonObject.class)
                                .map(json -> json.get("carnNbr"))
                                .map(String::valueOf)
                                .filter(cardNo::equals)
                                .isPresent()
                )
                .cast(JsonObject.class)
                .first()
                .then(jsonObject -> {

                    //业务逻辑
                });
    }
}
