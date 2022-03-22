package com.leaderli.demo;

import io.vavr.Tuple;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
public class TestVavr {
    public static class Person {

        public final String name;
        public final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person(" + name + ", " + age + ")";
        }

    }

    public static class PersonValidator {

        private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
        private static final int MIN_AGE = 0;

        public Validation<Seq<String>, Person> validatePerson(String name, int age) {
            return Validation.combine(validateName(name), validateAge(age)).ap(Person::new);
        }

        private Validation<String, String> validateName(String name) {
            return CharSeq.of(name).replaceAll(VALID_NAME_CHARS, "").transform(seq -> seq.isEmpty()
                    ? Validation.valid(name)
                    : Validation.invalid("Name contains invalid characters: '"
                    + seq.distinct().sorted() + "'"));
        }

        private Validation<String, Integer> validateAge(int age) {
            return age < MIN_AGE
                    ? Validation.invalid("Age must be at least " + MIN_AGE)
                    : Validation.valid(age);
        }

    }

    @Test
    public void test() {
        PersonValidator personValidator = new PersonValidator();

        // Valid(Person(John Doe, 30))
        Validation<Seq<String>, Person> valid = personValidator.validatePerson("John Doe", 30);
        if (valid.isValid()) {
            System.out.println(valid.get());

        }

         // Invalid(List(Name contains invalid characters: '!4?', Age must be greater than 0))
        Validation<Seq<String>, Person> invalid = personValidator.validatePerson("John? Doe!4", -1);
        if (invalid.isInvalid()) {
            System.out.println(invalid.getError());
        }

    }

    @Test
    public void test1() {

        Tuple.of("1");

    }

    @Test
    public void test2() {

        String result = Option.of("hello")
                .map(str -> (String) null)
                .getOrElse(() -> "world");
        System.out.println(result);
        Assertions.assertThat(result).isNull();

    }


}
