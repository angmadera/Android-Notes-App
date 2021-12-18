package com.example.christopher.recycler;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.StringWriter;

public class Notes {

    private final String name;
    private final String description;
    private final long dateTime;

    Notes(String name, String description, long dateTime) {
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    long getDateTime() {
        return dateTime;
    }

    @NonNull
    public String toString() {

        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("title").value(getName());
            jsonWriter.name("description").value(getDescription());
            jsonWriter.name("dateTime").value(getDateTime());
            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}