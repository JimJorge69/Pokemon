package com.example.appnaruto;


public class Character {

    public String id;
    public String name;
    public String base_experience;

    public String getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(String base_experience) {
        this.base_experience = base_experience;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String id, String name, String base_experience) {
        this.id = id;
        this.name = name;
        this.base_experience = base_experience;
    }
}
