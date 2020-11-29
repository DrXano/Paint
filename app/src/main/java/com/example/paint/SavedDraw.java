package com.example.paint;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SavedDraw implements Serializable {

    public String name;
    public HashMap<String,Draw> paths;

    public SavedDraw() {
    }

    public SavedDraw(String name, List<Draw> paths) {
        this.name = name;
        this.paths = new HashMap<>();
        for(int i = 0; i < paths.size(); i++){
            this.paths.put(String.valueOf(i) + "_key",paths.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,Draw> getPaths() {
        return this.paths;
    }

    public void setPaths(HashMap<String,Draw> paths) {
        this.paths = paths;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("paths", paths);

        return result;
    }
}
