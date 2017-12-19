package com.ptpthingers.yacs5e_app;

import com.google.gson.JsonObject;

/**
 * Created by leo on 18.12.17.
 */

public class Item {
    String name;
    String description;

    public Item(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public Item() {
        this.description = "";
        this.name = "";
    }

    static Item itemFromJsonObject(JsonObject jsonObject) {
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        if (name.equals("None")) {
            name = "";
        }
        return new Item(name, description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
