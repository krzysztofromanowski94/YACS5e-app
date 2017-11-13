package yacs5e.ptpthingers.com.yacs5e_app;

class SavingThrow {

    Boolean proficient; // TRUE if proficient, FALSE if not

    // Advantages and disadvantages:
    // 0 for nothing
    // 1 for enabling
    // -1 for locking the value in disabled state
    Byte advantage;
    Byte disadvantage;

    public SavingThrow() {
        proficient = Boolean.FALSE;
        advantage = 0;
        disadvantage = 0;
    }

    public Boolean getProficient() {
        return proficient;
    }

    public void setProficient(Boolean proficient) {
        this.proficient = proficient;
    }

    public Byte getAdvantage() {
        return advantage;
    }

    public void setAdvantage(Byte advantage) {
        this.advantage = advantage;
    }

    public Byte getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(Byte disadvantage) {
        this.disadvantage = disadvantage;
    }
}
