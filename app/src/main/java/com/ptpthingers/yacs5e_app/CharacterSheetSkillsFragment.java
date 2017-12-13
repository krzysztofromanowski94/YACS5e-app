package com.ptpthingers.yacs5e_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.annotation.Nullable;


public class CharacterSheetSkillsFragment extends Fragment {

    private static final String TAG = "SkillsFrag";
    public static final String JSON_FILE = "json_file";

    Character mCurrentChar;

    private TextView mAthletics,
            mAcrobatics, mSoH, mStealth,
            mArcana, mHistory, mInvestigation, mNature, mReligion,
            mAnimHandling, mInsight, mMedicine, mPerception, mSurvival,
            mDeception, mIntimidation, mPerformance, mPersuasion;


    public CharacterSheetSkillsFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetSkillsFragment newInstance(String json) {
        CharacterSheetSkillsFragment fragment = new CharacterSheetSkillsFragment();
        Bundle args = new Bundle();
        args.putString(JSON_FILE, json);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Gson gson = new Gson();
        String json = getArguments().getString(JSON_FILE);
        mCurrentChar = gson.fromJson(json, Character.class);

        return inflater.inflate(R.layout.fragment_character_sheet_skills, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Integer skillBonus;
        mAthletics = getView().findViewById(R.id.athletics_bonus);
        skillBonus = mCurrentChar.getStrScore().getModifier() + assignBonus("Athletics");
        mAthletics.setText(skillBonus.toString());

        mAcrobatics = getView().findViewById(R.id.acrobatics_bonus);
        skillBonus = mCurrentChar.getDexScore().getModifier() + assignBonus("Acrobatics");
        mAcrobatics.setText(skillBonus.toString());

        mSoH = getView().findViewById(R.id.sleight_of_hand_bonus);
        skillBonus = mCurrentChar.getDexScore().getModifier() + assignBonus("Sleight of Hand");
        mSoH.setText(skillBonus.toString());

        mStealth = getView().findViewById(R.id.stealth_bonus);
        skillBonus = mCurrentChar.getDexScore().getModifier() + assignBonus("Stealth");
        mStealth.setText(skillBonus.toString());

        mArcana = getView().findViewById(R.id.arcana_bonus);
        skillBonus = mCurrentChar.getIntScore().getModifier() + assignBonus("Arcana");
        mArcana.setText(skillBonus.toString());

        mHistory = getView().findViewById(R.id.history_bonus);
        skillBonus = mCurrentChar.getIntScore().getModifier() + assignBonus("History");
        mHistory.setText(skillBonus.toString());

        mInvestigation = getView().findViewById(R.id.investigation_bonus);
        skillBonus = mCurrentChar.getIntScore().getModifier() + assignBonus("Investigation");
        mInvestigation.setText(skillBonus.toString());

        mNature = getView().findViewById(R.id.nature_bonus);
        skillBonus = mCurrentChar.getIntScore().getModifier() + assignBonus("Nature");
        mNature.setText(skillBonus.toString());

        mReligion = getView().findViewById(R.id.religion_bonus);
        skillBonus = mCurrentChar.getIntScore().getModifier() + assignBonus("Religion");
        mReligion.setText(skillBonus.toString());

        mAnimHandling = getView().findViewById(R.id.animal_handling_bonus);
        skillBonus = mCurrentChar.getWisScore().getModifier() + assignBonus("Animal Handling");
        mAnimHandling.setText(skillBonus.toString());

        mInsight = getView().findViewById(R.id.insight_bonus);
        skillBonus = mCurrentChar.getWisScore().getModifier() + assignBonus("Insight");
        mInsight.setText(skillBonus.toString());

        mMedicine = getView().findViewById(R.id.medicine_bonus);
        skillBonus = mCurrentChar.getWisScore().getModifier() + assignBonus("Medicine");
        mMedicine.setText(skillBonus.toString());

        mPerception = getView().findViewById(R.id.perception_bonus);
        skillBonus = mCurrentChar.getWisScore().getModifier() + assignBonus("Perception");
        mPerception.setText(skillBonus.toString());

        mSurvival = getView().findViewById(R.id.survival_bonus);
        skillBonus = mCurrentChar.getWisScore().getModifier() + assignBonus("Survival");
        mSurvival.setText(skillBonus.toString());

        mDeception = getView().findViewById(R.id.deception_bonus);
        skillBonus = mCurrentChar.getChaScore().getModifier() + assignBonus("Deception");
        mDeception.setText(skillBonus.toString());

        mIntimidation = getView().findViewById(R.id.intimidation_bonus);
        skillBonus = mCurrentChar.getChaScore().getModifier() + assignBonus("Intimidation");
        mIntimidation.setText(skillBonus.toString());

        mPerformance = getView().findViewById(R.id.performance_bonus);
        skillBonus = mCurrentChar.getChaScore().getModifier() + assignBonus("Performance");
        mPerformance.setText(skillBonus.toString());

        mPersuasion = getView().findViewById(R.id.persuasion_bonus);
        skillBonus = mCurrentChar.getChaScore().getModifier() + assignBonus("Persuasion");
        mPersuasion .setText(skillBonus.toString());
    }

    public Integer assignBonus(String skill) {
        Proficiency mult = mCurrentChar.getSkills().get(skill);
        try {
            switch (mult) {
                case DOUBLE:
                    return 2 * mCurrentChar.getProfBonus();
                case FULL:
                    return mCurrentChar.getProfBonus();
                case HALF:
                    return mCurrentChar.getProfBonus() / 2;
                default:
                    return 0;
            }
        } catch (NullPointerException exception) {
            return 0;
        }
    }
}
