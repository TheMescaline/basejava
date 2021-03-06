package ru.javawebinar.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.model.Section;
import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder().registerTypeAdapter(Section.class, new JsonSectionAdapter()).create();

    private JsonParser() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T read(Reader reader, Class<T> tClass) {
        return GSON.fromJson(reader, tClass);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(String value, Class<T> tClass) {
        return GSON.fromJson(value, tClass);
    }

    public static <T> String write(T object, Class<T> tClass) {
        return GSON.toJson(object, tClass);
    }
}
