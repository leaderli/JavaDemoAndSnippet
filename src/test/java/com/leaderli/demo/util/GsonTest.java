package com.leaderli.demo.util;

import com.google.gson.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GsonTest {

    private static class MapJsonSerializer implements JsonSerializer {

        @Override
        public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
            return serialize(o,(Class)type);
        }


        private <T> JsonElement serialize(Object o, Class<T> type) {
            if (Map.class.isAssignableFrom(type)) {
                JsonObject jsonObject = new JsonObject();
                Map<?, ?> map = (Map) o;
                map.forEach((k, v) -> {
                    if (k == null) {
                        return;
                    }
                    String key = String.valueOf(k);
                    System.out.println("key = " + key);
                    if (v == null) {
                        jsonObject.add(key, new JsonPrimitive(""));
                    } else {
                        jsonObject.add(key, serialize(v, v.getClass()));
                    }

                });
                return jsonObject;
            } else if (Iterable.class.isAssignableFrom(type)) {
                Iterable<?> iterable = (Iterable) o;
                JsonArray array = new JsonArray();
                iterable.forEach(e -> {
                    if (e == null) {
                        array.add(new JsonPrimitive(""));
                    } else {

                        array.add(serialize(e, e.getClass()));
                    }

                });
                return array;
            } else {

                return new JsonPrimitive(String.valueOf(o));
            }
        }
    }

    @Test
    public void test() {

        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Map.class, new MapJsonSerializer()).create();

        Map map = new HashMap();
        map.put("str",null);
        map.put("set",new HashSet<>());
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(null);
        map.put("list", list);
        Map temp= new HashMap();
        temp.put("temp",null);
        map.put("temp",temp);

        System.out.println(gson.toJson(map));


    }
    @Test
    public void test1() {
        print("hello");
        print("测试测试测试测试测试测试测试测试测试");
    }
    private String mul(String origin,int times){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <times ; i++) {
            sb.append(origin);

        }
        return sb.toString();
    }
    private void  print(String msg){
        int table = 80;
        int len = msg.length();
        for (char c : msg.toCharArray()) {
            if (c > 127){
                //特殊字符占两个空格
                len++;
            }
        }

        //两个空格
        msg +=  mul(" ",table).substring(len);

        String info = "┏"+mul("━",table)+"┓\r\n";
        info+="┃"+msg+"┃\r\n";
        info += "┗"+mul("━",table)+"┛";
        System.out.println(info);

    }

    @Test
    public void test2(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = "{\n" +
                "  \"nextObjectId\": 1,\n" +
                "  \"flowNodes\": [\n" +
                "    {\n" +
                "      \"class\": \"\",\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"\",\n" +
                "      \"flowName\": \"\",\n" +
                "      \"icon\": \"\",\n" +
                "      \"gotos\": [\n" +
                "        { \"id\": 1, \"name\": \"default\", \"linkedNodeId\": 1 },\n" +
                "        { \"id\": 1, \"name\": \"default\" }\n" +
                "      ],\n" +
                "      \"pointX\": 100,\n" +
                "      \"pointY\": 100\n" +
                "    }\n" +
                "  ],\n" +
                "  \"connectionNodes\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"sourceId\": \"\",\n" +
                "      \"tartId\": \"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        Object obj = gson.fromJson(json, Object.class);
        System.out.println(obj);

        System.out.println(gson.toJson(obj));
    }
}
