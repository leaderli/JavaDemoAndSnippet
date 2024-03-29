package io.leaderli.demo;

import com.google.gson.JsonObject;
import io.leaderli.litool.core.meta.Lino;
import io.leaderli.litool.core.meta.Lira;

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
                                .absent()
                )
                .cast(JsonObject.class)
                .first().or(JsonObject::new).get();


        Lira.of(cardList)
                .filter(item ->
                        Lino.of(item)
                                .cast(JsonObject.class)
                                .map(json -> json.get("carnNbr"))
                                .map(String::valueOf)
                                .filter(cardNo::equals)
                                .present()
                )
                .cast(JsonObject.class)
                .first()
                .ifPresent(jsonObject -> {

                    //业务逻辑
                });
    }
}
