package com.ptpthingers.yacs5e_app;

/**
 * Created by leo on 13.12.17.
 */

class Attack {
    private String mName;
    private Integer mToHit;
    private String mRange;
    private AbilityScore mAbility;
    private Integer mDiceCount;
    private Integer mDiceType;
    private Integer mFlatBonus;
    private DamageType mDamageType;
    private Boolean mProficient;
    private Boolean mOffHand;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getmToHit() {
        return mToHit;
    }

    public void setmToHit(Integer mToHit) {
        this.mToHit = mToHit;
    }

    public String getmRange() {
        return mRange;
    }

    public void setmRange(String mRange) {
        this.mRange = mRange;
    }

    public AbilityScore getmAbility() {
        return mAbility;
    }

    public void setmAbility(AbilityScore mAbility) {
        this.mAbility = mAbility;
    }

    public Integer getmDiceCount() {
        return mDiceCount;
    }

    public void setmDiceCount(Integer mDiceCount) {
        this.mDiceCount = mDiceCount;
    }

    public Integer getmDiceType() {
        return mDiceType;
    }

    public void setmDiceType(Integer mDiceType) {
        this.mDiceType = mDiceType;
    }

    public Integer getmFlatBonus() {
        return mFlatBonus;
    }

    public void setmFlatBonus(Integer mFlatBonus) {
        this.mFlatBonus = mFlatBonus;
    }

    public DamageType getmDamageType() {
        return mDamageType;
    }

    public void setmDamageType(DamageType mDamageType) {
        this.mDamageType = mDamageType;
    }

    public Boolean getmProficient() {
        return mProficient;
    }

    public void setmProficient(Boolean mProficient) {
        this.mProficient = mProficient;
    }

    public Boolean getmOffHand() {
        return mOffHand;
    }

    public void setmOffHand(Boolean mOffHand) {
        this.mOffHand = mOffHand;
    }

    public Attack() {
        mName = "";
        mToHit = 0;
        mRange = "";
        mAbility = new AbilityScore(10);
        mDiceCount = 0;
        mDiceType = 0;
        mFlatBonus = 0;
        mDamageType = DamageType.PIERCING;
        mProficient = Boolean.FALSE;
        mOffHand = Boolean.FALSE;
    }
}
