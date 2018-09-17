package ru.javawebinar;

import ru.javawebinar.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.get(resume));
        field.set(resume, "reflected");
        System.out.println(field.get(resume));
        Method method = resume.getClass().getMethod("toString");
        method.setAccessible(true);
        System.out.println(method.invoke(resume));
    }
}
