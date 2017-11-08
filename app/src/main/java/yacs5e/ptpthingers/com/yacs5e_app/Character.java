package yacs5e.ptpthingers.com.yacs5e_app;

import java.util.Map;

class Character {

    String charName;
    String shortDesc;
    Integer portrait;
    Integer profBonus;

    AbilityScore strScore;
    AbilityScore dexScore;
    AbilityScore conScore;
    AbilityScore intScore;
    AbilityScore wisScore;
    AbilityScore chaScore;

    SavingThrow strSave;
    SavingThrow dexSave;
    SavingThrow conSave;
    SavingThrow intSave;
    SavingThrow wisSave;
    SavingThrow chaSave;

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
        //TODO: import character file and fill the object with parsed data
    }

    public Character() {
        charName = "Valindra";
        shortDesc = "Tiefling Wizard (Evocation) 3";
        portrait = R.drawable.side_nav_bar;
    }
}
