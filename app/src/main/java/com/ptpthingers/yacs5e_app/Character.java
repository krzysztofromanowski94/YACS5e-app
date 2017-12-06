package com.ptpthingers.yacs5e_app;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


class Character {
    private static final int defaultScore = 10;

    String charName;
    String shortDesc;
    Integer portrait;
    Integer profBonus;

    AbilityScore strScore, dexScore, conScore, intScore, wisScore, chaScore;
    SavingThrow strSave, dexSave, conSave, intSave, wisSave, chaSave;

    Map<String, Integer> levels;
    String race;
    List<Feature> traits;

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Integer getPortrait() {
        return portrait;
    }

    public void setPortrait(Integer portrait) {
        this.portrait = portrait;
    }

    public Integer getProfBonus() {
        return profBonus;
    }

    public void setProfBonus(Integer profBonus) {
        this.profBonus = profBonus;
    }

    public AbilityScore getStrScore() {
        return strScore;
    }

    public void setStrScore(AbilityScore strScore) {
        this.strScore = strScore;
    }

    public AbilityScore getDexScore() {
        return dexScore;
    }

    public void setDexScore(AbilityScore dexScore) {
        this.dexScore = dexScore;
    }

    public AbilityScore getConScore() {
        return conScore;
    }

    public void setConScore(AbilityScore conScore) {
        this.conScore = conScore;
    }

    public AbilityScore getIntScore() {
        return intScore;
    }

    public void setIntScore(AbilityScore intScore) {
        this.intScore = intScore;
    }

    public AbilityScore getWisScore() {
        return wisScore;
    }

    public void setWisScore(AbilityScore wisScore) {
        this.wisScore = wisScore;
    }

    public AbilityScore getChaScore() {
        return chaScore;
    }

    public void setChaScore(AbilityScore chaScore) {
        this.chaScore = chaScore;
    }

    public SavingThrow getStrSave() {
        return strSave;
    }

    public void setStrSave(SavingThrow strSave) {
        this.strSave = strSave;
    }

    public SavingThrow getDexSave() {
        return dexSave;
    }

    public void setDexSave(SavingThrow dexSave) {
        this.dexSave = dexSave;
    }

    public SavingThrow getConSave() {
        return conSave;
    }

    public void setConSave(SavingThrow conSave) {
        this.conSave = conSave;
    }

    public SavingThrow getIntSave() {
        return intSave;
    }

    public void setIntSave(SavingThrow intSave) {
        this.intSave = intSave;
    }

    public SavingThrow getWisSave() {
        return wisSave;
    }

    public void setWisSave(SavingThrow wisSave) {
        this.wisSave = wisSave;
    }

    public SavingThrow getChaSave() {
        return chaSave;
    }

    public void setChaSave(SavingThrow chaSave) {
        this.chaSave = chaSave;
    }

    public Map getLevels() {
        return levels;
    }

    public void setLevels(Map levels) {
        this.levels = levels;
    }

    public List<Feature> getTraits() {
        return traits;
    }

    public void setTraits(List<Feature> traits) {
        this.traits = traits;
    }

    public Character() {
        this.charName = "MyCharacter";

        this.strScore = new AbilityScore(defaultScore);
        this.dexScore = new AbilityScore(defaultScore);
        this.conScore = new AbilityScore(defaultScore);
        this.intScore = new AbilityScore(defaultScore);
        this.wisScore = new AbilityScore(defaultScore);
        this.chaScore = new AbilityScore(defaultScore);
        this.strSave = new SavingThrow();
        this.dexSave = new SavingThrow();
        this.conSave = new SavingThrow();
        this.intSave = new SavingThrow();
        this.wisSave = new SavingThrow();
        this.chaSave = new SavingThrow();

        this.race = "";
        this.levels = new HashMap<>();

        this.shortDesc = "";
        this.traits = new LinkedList<>();

    }

    public Character(int charFile) {
        /* TODO: import character file and fill the object with parsed data
        https://www.androidhive.info/2012/01/android-json-parsing-tutorial/ */
    }
}
