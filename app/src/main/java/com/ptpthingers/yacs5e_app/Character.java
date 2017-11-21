package com.ptpthingers.yacs5e_app;

import java.util.Map;

class Character {

    String charName;
    String shortDesc;
    Integer portrait;
    Integer profBonus;

    AbilityScore strScore, dexScore, conScore, intScore, wisScore, chaScore;

    SavingThrow strSave, dexSave, conSave, intSave, wisSave, chaSave;

    Map levels;


    public String getCharName() {
        return charName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Integer getPortrait() {
        return portrait;
    }

    public Character(int charFile) {
        /* TODO: import character file and fill the object with parsed data
        https://www.androidhive.info/2012/01/android-json-parsing-tutorial/ */
    }
}
