package yacs5e.ptpthingers.com.yacs5e_app;

class Features {
    private int Index;
    private String Name;
    private int Level;
    private String CharacterClass;
    private String Description;

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getCharacterClass(){
        return CharacterClass;
    }

    public void setClass(String aClass) {
        CharacterClass = aClass;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}