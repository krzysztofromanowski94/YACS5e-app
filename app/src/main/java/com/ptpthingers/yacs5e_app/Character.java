package com.ptpthingers.yacs5e_app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ptpthingers.synchronization.CharacterEntity;
import com.ptpthingers.synchronization.DBWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


class Character {
    private static final int defaultScore = 14;

    private String mCharName;
    private String mShortDesc;
    private Integer mPortrait;
    private Integer mProfBonus;

    private Integer mMaxHealth;
    private Integer mCurrentHealth;
    private Integer mTempHealth;
    private Integer mArmorClass;
    private Integer mSpeed;
    private Integer mInitiative;
    private HashMap<String, Integer> mHitDice;
    private List<Attack> mAttacks;


    private AbilityScore strScore, dexScore, conScore, intScore, wisScore, chaScore;
    private SavingThrow strSave, dexSave, conSave, intSave, wisSave, chaSave;
    private HashMap<String, Proficiency> skills;
    private List<String> miscProfs;

    private HashMap<HashMap<String, String>, Integer> levels;
    private String race;
    private List<Feature> traits;
    private String mUuid;

    public Character() {
        initializeCharacter();
    }


    public Character(String mUuid) {
        initializeCharacter();
        this.mUuid = mUuid;

        String blob = DBWrapper.getCharEntity(mUuid).getData();
        initializeCharacter(blob);
    }

    public String getmUuid() {
        return mUuid;
    }

    public String getCharName() {
        return mCharName;
    }

    public void setCharName(String charName) {
        this.mCharName = charName;
    }

    public String getShortDesc() {
        return mShortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.mShortDesc = shortDesc;
    }

    public Integer getPortrait() {
        return mPortrait;
    }

    public void setPortrait(Integer portrait) {
        this.mPortrait = portrait;
    }

    public Integer getProfBonus() {
        return mProfBonus;
    }

    public void setProfBonus(Integer profBonus) {
        this.mProfBonus = profBonus;
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

    public List<Feature> getTraits() {
        return traits;
    }

    public void setTraits(List<Feature> traits) {
        this.traits = traits;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public HashMap<String, Proficiency> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String, Proficiency> skills) {
        this.skills = skills;
    }

    public HashMap<HashMap<String, String>, Integer> getLevels() {
        return levels;
    }

    public void setLevels(HashMap<HashMap<String,String>, Integer> levels) {
        this.levels = levels;
    }

    public Integer getmProfBonus() {
        return mProfBonus;
    }

    public void setmProfBonus(Integer mProfBonus) {
        this.mProfBonus = mProfBonus;
    }

    public Integer getmArmorClass() {
        return mArmorClass;
    }

    public void setmArmorClass(Integer mArmorClass) {
        this.mArmorClass = mArmorClass;
    }

    public Integer getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(Integer mSpeed) {
        this.mSpeed = mSpeed;
    }

    public Integer getmInitiative() {
        return mInitiative;
    }

    public void setmInitiative(Integer mInitiative) {
        this.mInitiative = mInitiative;
    }

    public HashMap<String, Integer> getmHitDice() {
        return mHitDice;
    }

    public void setmHitDice(HashMap<String, Integer> mHitDice) {
        this.mHitDice = mHitDice;
    }

    public List<Attack> getmAttacks() {
        return mAttacks;
    }

    public void setmAttacks(List<Attack> mAttacks) {
        this.mAttacks = mAttacks;
    }

    public Integer getmMaxHealth() {
        return mMaxHealth;
    }

    public void setmMaxHealth(Integer mMaxHealth) {
        this.mMaxHealth = mMaxHealth;
    }

    public Integer getmCurrentHealth() {
        return mCurrentHealth;
    }

    public void setmCurrentHealth(Integer mCurrentHealth) {
        this.mCurrentHealth = mCurrentHealth;
    }

    public Integer getmTempHealth() {
        return mTempHealth;
    }

    public void setmTempHealth(Integer mTempHealth) {
        this.mTempHealth = mTempHealth;
    }

    public List<String> getMiscProfs() {
        return miscProfs;
    }

    public void setMiscProfs(List<String> miscProfs) {
        this.miscProfs = miscProfs;
    }

    public String post(String ownerLogin) {
        if (mUuid == null) {
            mUuid = UUID.randomUUID().toString();
        }
        DBWrapper.insertCharEntity(new CharacterEntity(
                mUuid,
                ownerLogin,
                new GsonBuilder().create().toJson(this)));
        return mUuid;
    }

    private void initializeCharacter() {
        this.mCharName = "MyCharacter";

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

        this.mMaxHealth = 0;
        this.mCurrentHealth = 0;
        this.mTempHealth = 0;
        this.mArmorClass = 0;
        this.mSpeed = 0;
        this.mInitiative = 0;
        this.mHitDice = new HashMap<>();
        this.mAttacks = new ArrayList<>();

        this.race = "";
        this.levels = new HashMap<>();
        this.skills = new HashMap<>();
        this.miscProfs = new LinkedList<>();
        this.mHitDice = new HashMap<>();
        this.mProfBonus = 2;

        this.mShortDesc = "";
        this.traits = new LinkedList<>();
    }

    private void initializeCharacter(String blob) {
        Character tempChar = new Gson().fromJson(blob, Character.class);

        this.mCharName = tempChar.getCharName();

        this.strScore = tempChar.getStrScore();
        this.dexScore = tempChar.getDexScore();
        this.conScore = tempChar.getConScore();
        this.intScore = tempChar.getIntScore();
        this.wisScore = tempChar.getWisScore();
        this.chaScore = tempChar.getChaScore();
        this.strSave = tempChar.getStrSave();
        this.dexSave = tempChar.getDexSave();
        this.conSave = tempChar.getConSave();
        this.intSave = tempChar.getIntSave();
        this.wisSave = tempChar.getWisSave();
        this.chaSave = tempChar.getChaSave();

        this.mMaxHealth = tempChar.getmMaxHealth();
        this.mCurrentHealth = tempChar.getmCurrentHealth();
        this.mTempHealth = tempChar.getmTempHealth();
        this.mArmorClass = tempChar.getmArmorClass();
        this.mSpeed = tempChar.getmSpeed();
        this.mInitiative = tempChar.getmInitiative();
        this.mHitDice = tempChar.getmHitDice();
        this.mAttacks = tempChar.getmAttacks();

        this.race = tempChar.getRace();
        this.levels = tempChar.getLevels();
        this.skills = tempChar.getSkills();
        this.mHitDice = tempChar.getmHitDice();
        this.mProfBonus = tempChar.getmProfBonus();

        this.mShortDesc = tempChar.mShortDesc;
        this.traits = tempChar.getTraits();
    }

    @Override
    public String toString() {
        return "Character{" +
                "mCharName='" + mCharName + '\'' +
                ", mShortDesc='" + mShortDesc + '\'' +
                ", mUuid='" + mUuid + '\'' +
                '}';
    }
}
