package io.leaderli.demo.util;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.leaderli.demo.bean.Person;
import io.leaderli.litool.core.internal.ParameterizedTypeImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.*;

public class GsonTest {

    private static class MapJsonSerializer implements JsonSerializer {

        @Override
        public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
            return serialize(o, (Class) type);
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
        map.put("str", null);
        map.put("set", new HashSet<>());
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(null);
        map.put("list", list);
        Map temp = new HashMap();
        temp.put("temp", null);
        map.put("temp", temp);

        System.out.println(gson.toJson(map));


    }

    @Test
    public void test1() {
        print("hello");
        print("测试测试测试测试测试测试测试测试测试");
    }

    private String mul(String origin, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(origin);

        }
        return sb.toString();
    }

    private void print(String msg) {
        int table = 80;
        int len = msg.length();
        for (char c : msg.toCharArray()) {
            if (c > 127) {
                //特殊字符占两个空格
                len++;
            }
        }

        //两个空格
        msg += mul(" ", table).substring(len);

        String info = "┏" + mul("━", table) + "┓\r\n";
        info += "┃" + msg + "┃\r\n";
        info += "┗" + mul("━", table) + "┛";
        System.out.println(info);

    }

    @Test
    public void test2() {
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


    @Test
    public void test3() {
        Gson gson = new Gson();
        Person person = new Person();
        person.setAAge(10);
        person.setFuck("li");
        String s = gson.toJson(person);
        System.out.println(s);


        Person person1 = gson.fromJson(s, Person.class);
        System.out.println(person1);
        Type type = Person.class;
        Object o = gson.fromJson(s, type);
        System.out.println(o);

    }

    private class MyTypeAdapterFactory implements TypeAdapterFactory {

        public MyTypeAdapterFactory() {
        }

        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() == Object1.class) {
                return ((TypeAdapter<T>) new MyTypeAdapter());
            }
            return null;
        }

        private class MyTypeAdapter extends TypeAdapter<Object> {

            @Override
            public void write(JsonWriter out, Object value) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object read(JsonReader in) throws IOException {
                JsonToken token = in.peek();
                switch (token) {
                    case BEGIN_ARRAY:
                        List<Object> list = new ArrayList<Object>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(read(in));
                        }
                        in.endArray();
                        return list;

                    case BEGIN_OBJECT:
                        Map<String, Object> map = new LinkedTreeMap<String, Object>();
                        in.beginObject();
                        while (in.hasNext()) {
                            map.put(in.nextName(), read(in));
                        }
                        in.endObject();
                        return map;

                    case STRING:
                    case NUMBER:
                    case BOOLEAN:
                        return in.nextString();

                    case NULL:
                        in.nextNull();
                        return "";

                    default:
                        throw new IllegalStateException();
                }
            }

            ;
        }
    }

    @Test
    void test11118() {

        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                System.out.write(b);
            }
        }));
        System.err.println("123123");
    }

    @Test
    void testInt() {


        //language=JSON
        String b = "{\"a\": 1,\"b\": null,\"c\": [{\"a\":\"1\",\"b\":\"\",\"c\":[]},{\"a\":2,\"b\":\"\",\"c\":[]}],\"d\": {\"a\":1,\"b\":null,\"c\":[]}}";

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new MyTypeAdapterFactory()).create();


        Map<String, Object>
                typeToken = gson.fromJson(b, new HashMap<String, Object>() {
        }.getClass());
        System.out.println(typeToken);
        System.out.println(gson.fromJson(b, Map.class));

        ParameterizedTypeImpl make = ParameterizedTypeImpl.make(null, Map.class, String.class, Object1.class);
        Object t = gson.fromJson(b, make);
        System.out.println(gson.toJson(t));

    }

    class Object1 {

    }

}
