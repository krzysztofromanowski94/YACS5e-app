package com.ptpthingers.yacs5e_app;

import java.util.List;

/**
 * Created by Kacper on 15.11.2017.
 */

public class Background {
    private Feature Feature;
    private List<String> PersonalityTrait;
    private List<String> Proficiency;

    public Feature getFeature() {
        return Feature;
    }

    public void setFeature(Feature feature) {
        Feature = feature;
    }

    public List<String> getPersonalityTrait() {
        return PersonalityTrait;
    }

    public void setPersonalityTrait(List<String> personalityTrait) {
        PersonalityTrait = personalityTrait;
    }

    public void setPersonalityTrait(String personalityTrait) {
        PersonalityTrait.add(personalityTrait);
    }

    public List<String> getProficiency() {
        return Proficiency;
    }

    public void setProficiency(List<String> proficiency) {
        Proficiency = proficiency;
    }

    public void setProficiency(String proficiency) {
        Proficiency.add(proficiency);
    }
}
