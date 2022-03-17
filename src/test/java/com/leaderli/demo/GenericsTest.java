package com.leaderli.demo;

import java.util.function.Function;

public class GenericsTest<K, V> {
    public static void main(String[] args) {
        
        Bean<CharSequence, CharSequence> bean = new Bean<>();
        //mapper.apply请求参数为Object，返回参数为String
        //bean.apply请求参数CharSequence可以向上转型为Object，mapper返回参数String可以向上转型为CharSequenc
        bean.mapper = (Function<Object, String>) k -> "_" + k + "_";
        CharSequence apply = bean.apply("1");
    }
    
    
    static class Bean<K, V> {
        Function<? super K, ? extends V> mapper;
        
        //对于参数k，一定是实际mapper的实际泛型K1的子类
        //对于返回值v，一定是mapper的实际泛型V1的父类
        //因此我们可以将k向上转型为K1，mapper的返回值V1也可以向上转型为V
        V apply(K k) {
            return mapper.apply(k);
        }
        
    }
    
}
