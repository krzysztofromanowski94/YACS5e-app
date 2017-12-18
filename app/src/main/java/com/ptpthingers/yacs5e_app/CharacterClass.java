package com.ptpthingers.yacs5e_app;

import java.util.List;

/**
 * Created by leo on 18.12.17.
 */

public class CharacterClass {
    private List<Integer> abilityScoreIncreases;
    private List<String> miscProfs;
    private Integer hitDiceSize;
    private String name;
    private List<Item> savingThrowProfs;
    private List<String> skillProfs;
    private List<CharacterSubclass> subclasses;

    public List<Integer> getAbilityScoreIncreases() {
        return abilityScoreIncreases;
    }

    public List<String> getMiscProfs() {
        return miscProfs;
    }

    public Integer getHitDiceSize() {
        return hitDiceSize;
    }

    public String getName() {
        return name;
    }

    public List<Item> getSavingThrowProfs() {
        return savingThrowProfs;
    }

    public List<String> getSkillProfs() {
        return skillProfs;
    }

    public List<CharacterSubclass> getSubclasses() {
        return subclasses;
    }

    public class CharacterSubclass {
        private String name;
        private String description;

        public CharacterSubclass(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
