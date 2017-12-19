package com.ptpthingers.yacs5e_app;


public class CharacterSubclass {
    private String name;
    private String description;

    public CharacterSubclass(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CharacterSubclass{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}