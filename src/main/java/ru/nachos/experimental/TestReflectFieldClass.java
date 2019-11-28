package ru.nachos.experimental;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestReflectFieldClass {

    private int firstField;
    private String secondField;
    private Object thirdField;

    public List<String> getFields(String name) {
        return Arrays.asList(this.getClass().getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toList());
    }

    public static void main(String[] args) throws NoSuchFieldException {
        TestReflectFieldClass clazz = new TestReflectFieldClass();
        List<String> firstField = clazz.getFields("firstField");
        System.out.println(firstField);
    }

}
