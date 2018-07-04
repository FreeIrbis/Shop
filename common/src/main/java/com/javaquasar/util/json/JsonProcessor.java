package com.javaquasar.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Java Quasar on 07.08.17.
 */
public class JsonProcessor {

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString
     * @return
     */
    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    public static void writeJsonToFile(String path, String json) {
        try (FileOutputStream fos = new FileOutputStream(path);
             Writer writer = new OutputStreamWriter(fos, "UTF-8")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(json, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeObjectToFile(String path, Object o) {
        try(FileWriter fw = new FileWriter(path)) {
            Gson gson = new Gson();
            gson.toJson(o, fw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readObjectFromJson(String json, Class<T> clazz) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, clazz);
    }

    public static String convertObjectToJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static void writePrettyJsonToFile(String path, String json) {
        writeJsonToFile(path, toPrettyFormat(json));
    }

}
