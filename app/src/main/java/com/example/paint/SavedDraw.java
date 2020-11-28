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
    public Map<String,Draw> paths;

    public SavedDraw() {
    }

    public SavedDraw(String name, List<Draw> paths) {
        this.name = name;
        this.paths = new HashMap<>();
        for(int i = 0; i < paths.size(); i++){
            this.paths.put(i+"",paths.get(i));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Draw> getPaths() {
        return new LinkedList<>(this.paths.values());
    }

    public void setPaths(List<Draw> paths) {
        this.paths = new HashMap<>();
        for(int i = 0; i < paths.size(); i++){
            this.paths.put(i+"",paths.get(i));
        }
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("paths", paths);

        return result;
    }
}
