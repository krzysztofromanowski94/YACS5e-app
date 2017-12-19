package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class CharacterClass {
    private List<Integer> abilityScoreIncreases;
//    private List<String> miscProfs;
    private Integer hitDiceSize;
    private String name;
    private List<String> savingThrowProfs;
//    private List<String> skillProfs;
    private List<CharacterSubclass> subclasses;

    public List<Integer> getAbilityScoreIncreases() {
        return abilityScoreIncreases;
    }

//    public List<String> getMiscProfs() {
//        return miscProfs;
//    }

    public Integer getHitDiceSize() {
        return hitDiceSize;
    }

    public String getName() {
        return name;
    }

    public List<String> getSavingThrowProfs() {
        return savingThrowProfs;
    }

/*    public List<String> getSkillProfs() {
        return skillProfs;
    }*/

    public List<CharacterSubclass> getSubclasses() {
        return subclasses;
    }

    ////

    static List<CharacterClass> makeCharacterClassList(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CompletePHBSquireClasses.json");
        } catch (IOException e) {
            Log.d("Character", "Can't open asset .json");
            return null;
        }

        List<CharacterClass> returnCharacterClassList = new ArrayList<>();

        JsonElement root = new JsonParser().parse(Utils.stringFromInputStream(inputStream));
        JsonArray jsonCharacterClassArray = root.getAsJsonObject().get("objects").getAsJsonArray();

        for (JsonElement element : jsonCharacterClassArray) {
            JsonObject raceJsonObj = element.getAsJsonObject();
            returnCharacterClassList.add(singleCharacterClass(raceJsonObj));
        }
        return returnCharacterClassList;
    }

    private static CharacterClass singleCharacterClass(JsonObject characterClassJsonObj) {
        Set<String> characterClassKeySet = characterClassJsonObj.keySet();
        CharacterClass characterClass = new CharacterClass();

        for (String key : characterClassKeySet) {
            switch (key) {
                case "type":
                    // Obviously type is CharacterClass
                    break;
                case "abilityScoreIncreases":
                    characterClass.abilityScoreIncreases = new ArrayList<>();
                    for (JsonElement jsonElement : characterClassJsonObj.getAsJsonArray(key)) {
                        characterClass.abilityScoreIncreases.add(jsonElement.getAsInt());
                    }
                    break;
                case "armorProfs":
                    break;
                case "armorSecondaryProfs":
                    break;
                case "armorTypeProfs":
                    break;
                case "armorTypeSecondaryProfs":
                    break;
                case "casterType":
                    break;
                case "classSpells":
                    break;
                case "hitDice":
                    break;
                case "hitDiceSize":
                    characterClass.hitDiceSize = characterClassJsonObj.getAsJsonPrimitive(key).getAsInt();
                    break;
                case "hpAtFirst":
                    break;
                case "hpAtFirstModifierId":
                    break;
                case "hpGainDice":
                    break;
                case "hpGainDiceSize":
                    break;
                case "hpGainModifierId":
                    break;
                case "languageProfs":
                    break;
                case "languageSecondaryProfs":
                    break;
                case "name":
                    characterClass.name = characterClassJsonObj.getAsJsonPrimitive(key).getAsString();
                    break;
                case "requiresSpellPreparation":
                    break;
                case "savingThrowProfs":
                    characterClass.savingThrowProfs = new ArrayList<>();
                    for (JsonElement jsonElement : characterClassJsonObj.getAsJsonArray(key)) {
                        characterClass.savingThrowProfs.add(jsonElement.getAsJsonObject().get("name").getAsString());
                    }
                    break;
                case "savingThrowSecondaryProfs":
                    break;
                case "skillProfs":
                    break;
                case "skillSecondaryProfs":
                    break;
                case "spellCastingAbilityId":
                    break;
                case "subclasses":
                    characterClass.subclasses = new ArrayList<>();
                    for (JsonElement jsonElement : characterClassJsonObj.getAsJsonArray(key)) {
                        String name = jsonElement.getAsJsonObject().get("name").getAsString();
                        String description = jsonElement.getAsJsonObject().get("description").getAsString();
                        characterClass.subclasses.add(new CharacterSubclass(name, description));
                    }
                        break;
                case "toolProfs":
                    break;
                case "toolSecondaryProfs":
                    break;
                case "weaponProfs":
                    break;
                case "weaponSecondaryProfs":
                    break;
                case "weaponTypeProfs":
                    break;
                case "weaponTypeSecondaryProfs":
                    break;
                case "classSpellIds":
                    break;

                default:
                    Log.i("CharacterClass", "Unknown switch case");
            }
        }
        return characterClass;
    }

}
