package io.leaderli.demo.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {

    default Stream<T> ofList(T[] arr){
        return  this;
    };
    default Stream<T> filter(Predicate<T> predicate){
        return this;
    };
    default <R>Stream<R> mapper(Function<? super  T,R> mapper){
        return null;
    };
    default void forEach(Consumer<T> consumer){
    };

    default Consumer<?> sink(){
        return null;
    }


}
