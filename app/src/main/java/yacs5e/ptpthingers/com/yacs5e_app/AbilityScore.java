package yacs5e.ptpthingers.com.yacs5e_app;

class AbilityScore {
    Integer value;
    Integer modifier;
    Integer tempValue;
    Integer tempModifier;
    Boolean useTemp;

    public AbilityScore() {
        setValue(10);
        setTempValue(0);
        this.useTemp = Boolean.FALSE;
    }

    public AbilityScore(Integer value, Integer tempValue, Boolean useTemp) {
        setValue(value);
        setTempValue(tempValue);
        this.useTemp = Boolean.FALSE;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        this.modifier = ((value-10)/2);
    }

    public Integer getModifier() {
        return modifier;
    }

    public Integer getTempValue() {
        return tempValue;
    }

    public void setTempValue(Integer tempValue) {
        this.tempValue = tempValue;
        this.tempModifier = ((tempValue-10)/2);
    }

    public Integer getTempModifier() {
        return tempModifier;
    }

    public Boolean getUseTemp() {
        return useTemp;
    }

    public void setUseTemp(Boolean useTemp) {
        this.useTemp = useTemp;
    }
}
