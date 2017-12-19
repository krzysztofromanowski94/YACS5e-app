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


class Feature {
    private Item background;
    private Category category;
    private Item characterClass;
    private String name;
    private String description;
    private int minimumLevel;
    private String race;
    private Item subclass;
    private String range;

    public Feature() {
        this.name = "";
        this.description = "";
        this.race = "";
        this.range = "";
    }

    @Override
    public String toString() {
        return "Feature{" +
                "background=" + background +
                ", category=" + category +
                ", characterClass=" + characterClass +
                ", name='" + name + '\'' +
                ", minimumLevel=" + minimumLevel +
                ", race='" + race + '\'' +
                ", subclass=" + subclass +
                ", range='" + range + '\'' +
                '}';
    }

    static List<Feature> makeFeatureList(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CompletePHBSquireFeatures.json");
        } catch (IOException e) {
            Log.d("Feature", "Can't open asset .json");
            return null;
        }

        List<Feature> returnFeatureList = new ArrayList<>();

        JsonElement root = new JsonParser().parse(Utils.stringFromInputStream(inputStream));
        JsonArray jsonFeatureArray = root.getAsJsonObject().get("objects").getAsJsonArray();

        for (JsonElement element : jsonFeatureArray) {
            JsonObject featureJsobObj = element.getAsJsonObject();
            returnFeatureList.add(singleFeature(featureJsobObj));
        }
        return returnFeatureList;
    }

    static List<Feature> makeFeatureListForCategory(Context context, Category category) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CompletePHBSquireFeatures.json");
        } catch (IOException e) {
            Log.d("Feature", "Can't open asset .json");
            return null;
        }

        List<Feature> returnFeatureList = new ArrayList<>();

        JsonElement root = new JsonParser().parse(Utils.stringFromInputStream(inputStream));
        JsonArray jsonFeatureArray = root.getAsJsonObject().get("objects").getAsJsonArray();

        for (JsonElement element : jsonFeatureArray) {
            JsonObject featureJsobObj = element.getAsJsonObject();
            Feature feature = singleFeature(featureJsobObj);
            if (feature.category == category) {
                returnFeatureList.add(feature);
            }
        }
        return returnFeatureList;
    }

    private static Feature singleFeature(JsonObject featureJsonObj){
        Set<String> featureKeySet = featureJsonObj.keySet();
        Feature feature = new Feature();
        for (String featureKey : featureKeySet ) {
            switch (featureKey) {
                case "type":
                    // Obviously type is Feature
                    break;
                case "areaOfEffect":
                    // Ignore
                    break;
                case "background":
                    feature.background = Item.itemFromJsonObject(featureJsonObj.getAsJsonObject(featureKey));
                    break;
                case "category":
                    feature.category = Category.valueOf(featureJsonObj.getAsJsonPrimitive(featureKey).getAsString());
                    break;
                case "characterClass":
                    feature.characterClass = Item.itemFromJsonObject(featureJsonObj.getAsJsonObject(featureKey));
                    break;
                case "description":
                    feature.description = featureJsonObj.getAsJsonPrimitive(featureKey).getAsString();
                    break;
                case "hasAreaOfEffect":
                    // Ignore
                    break;
                case "minimumLevel":
                    feature.minimumLevel = featureJsonObj.getAsJsonPrimitive(featureKey).getAsInt();
                    break;
                case "name":
                    feature.name = featureJsonObj.getAsJsonPrimitive(featureKey).getAsString();
                    break;
                case "race":
                    feature.race = featureJsonObj.getAsJsonObject(featureKey).get("name").getAsString();
                    break;
                case "range":
                    feature.range = featureJsonObj.getAsJsonPrimitive(featureKey).getAsString();
                    break;
                case "subclass":
                    feature.subclass = Item.itemFromJsonObject(featureJsonObj.getAsJsonObject(featureKey));
                    break;
                case "ranged":
                    // Ignore
                    break;

                default:
                    Log.i("Feature", "Switch I don't know: " + featureKey);
            }
        }
        return feature;
    }
}
