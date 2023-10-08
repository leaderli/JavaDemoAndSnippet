package io.leaderli.demo;

import io.leaderli.litool.core.bit.BitStr;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leaderli
 * @since 2022/1/26
 */
public class BitTest {

    @Test
    public void test1() {

        Assertions.assertSame(0b1110, 0b1111 & ~0b0001);
        Assertions.assertSame(0b0110, 0b0111 & ~0b0001);
        Assertions.assertSame(0b0110, 0b0111 & ~0b1001);
    }

    @Test
    public void test() {

        int x = 0xFF;

        System.out.println(Integer.toBinaryString(x));
        System.out.println(Integer.toBinaryString(~x));

        int bi = 0b0001;
        System.out.println(bi);
        String replace = String.format("%16s", Integer.toBinaryString(11)).replace(' ', '0');
        System.out.println(replace);

        System.out.println(replace.indexOf("1"));
        System.out.println(replace.indexOf("1", replace.indexOf("1") + 10));

    }

    public Integer toI(Field field) {
        try {
            return Integer.valueOf((Integer) field.get(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Test
    public void test3() throws IllegalAccessException {

        String a = Arrays.stream(IResourceDelta.class.getFields()).sorted((f1, f2) -> {
            return toI(f1) - toI(f2);
        }).map(f -> f.getName()).collect(Collectors.joining("\",\""));


        System.out.println(a);

        Map<Integer, String> map = new HashMap<>();
        for (Field field : IResourceDelta.class.getFields()) {

            map.put(toI(field), field.getName());

        }
        System.out.println(map);

        List<String> sb = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            sb.add("\"" + map.getOrDefault(1 << i, "") + "\"");
        }
        System.out.println(StringUtils.join(sb,","));
        System.out.println(BitStr.of(IResourceDelta.class));

    }


    public interface IResourceDelta {

        public static final int NO_CHANGE = 0x0;

        public static final int ADDED = 0x1;


        public static final int REMOVED = 0x2;


        public static final int CHANGED = 0x4;


        public static final int ADDED_PHANTOM = 0x8;


        public static final int REMOVED_PHANTOM = 0x10;


        public static final int CONTENT = 0x100;

        public static final int MOVED_FROM = 0x1000;

        public static final int MOVED_TO = 0x2000;


        public static final int COPIED_FROM = 0x800;

        public static final int OPEN = 0x4000;


        public static final int TYPE = 0x8000;

        public static final int SYNC = 0x10000;


        public static final int MARKERS = 0x20000;


        public static final int REPLACED = 0x40000;


        public static final int DESCRIPTION = 0x80000;


        public static final int ENCODING = 0x100000;


        public static final int LOCAL_CHANGED = 0x200000;

        public static final int DERIVED_CHANGED = 0x400000;


    }

}
