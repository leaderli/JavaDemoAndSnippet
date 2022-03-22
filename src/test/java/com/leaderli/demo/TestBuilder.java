package com.leaderli.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBuilder {

    public abstract static class BuilderLink<K, V> {
        protected abstract void set(K k, V v);

        public V get(K k) {
            throw new UnsupportedOperationException();
        }

    }

    static class LiDetailBuilder extends BuilderLink<String, String> {
        final LiBuilder liBuilder;
        final BuilderLink<String, String> builderLink;

        LiDetailBuilder(LiBuilder liBuilder, BuilderLink<String, String> builderLink) {
            this.liBuilder = liBuilder;
            this.builderLink = builderLink;
        }

        public LiDetailBuilder put(String k, String v) {
            set(k, StringUtils.trimToEmpty(v));
            return this;
        }

        public LiBuilder back() {
            return liBuilder;
        }

        @Override
        protected void set(String k, String v) {
            this.builderLink.set(k, v);
        }
    }

    static class LiBuilder extends BuilderLink<String, Object> {

        final BuilderLink<String, Object> builderLink;

        public static LiBuilder mock() {


            return new LiBuilder(new BuilderLink<String, Object>() {
                @Override
                protected void set(String s, Object o) {
                    map.put(s, o);
                }

                @Override
                public Object get(String s) {
                    return map.get(s);
                }
            }
            );
        }

        private LiBuilder(BuilderLink<String, Object> builderLink) {
            this.builderLink = builderLink;
        }

        public LiBuilder returnCode(String code) {
            this.builderLink.set("returnCode", code);
            return this;
        }

        public LiBuilder returnMsg(String msg) {
            this.builderLink.set("returnMsg", msg);
            return this;
        }

        public LiBuilder success() {
            return returnCode("0000").returnMsg("交易成功");
        }

        public LiBuilder error() {
            return returnCode("9000").returnMsg("交易失败");
        }

        public LiBuilder detail(List<Map<String, String>> detail) {
            if (detail == null) {
                detail = new ArrayList<>();
            }
            set("Detail", detail);
            this.detail = detail;
            return this;
        }

        public LiDetailBuilder detail(Map<String, String> element) {
            detail(detail);
            this.detail.add(element);
            return new LiDetailBuilder(this, new BuilderLink<String, String>() {
                @Override
                protected void set(String s, String s2) {
                    element.put(s, s2);
                }
            });
        }

        List<Map<String, String>> detail;

        public LiDetailBuilder detail() {

            this.detail(detail);
            HashMap<String, String> element = new HashMap<>();
            return new LiDetailBuilder(this, new BuilderLink<String, String>() {
                @Override
                protected void set(String s, String s2) {
                    element.put(s, s2);
                }
            });

        }


        @Override
        protected void set(String s, Object o) {
            this.builderLink.set(s, o);
        }

        @Override
        public Object get(String s) {
            return builderLink.get(s);
        }
    }

    static Map<String, Object> map = new HashMap<>();

    @Test
    public void test() {
        LiBuilder liBuilder = LiBuilder.mock().returnCode("0000");
        System.out.println(map);

        liBuilder.success();
        System.out.println(map);

        liBuilder.error();
        System.out.println(map);


        liBuilder.returnMsg("大傻逼").returnCode("213");
        System.out.println(map);

        liBuilder.detail().put("fuck", null);
        System.out.println(map);
        liBuilder.detail().put("fuck2", "hh");
        System.out.println(map);
        liBuilder.detail().put("fuck", "hh").put("fuck2", null).back().returnCode("9999").detail().put("9999", "9999");

        System.out.println(map);
    }
}
