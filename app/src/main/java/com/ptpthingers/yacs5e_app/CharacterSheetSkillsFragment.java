package com.ptpthingers.yacs5e_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

import java.util.Locale;

import javax.annotation.Nullable;


public class CharacterSheetSkillsFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    Character mCurrentChar;


    public CharacterSheetSkillsFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetSkillsFragment newInstance(String uuid) {
        CharacterSheetSkillsFragment fragment = new CharacterSheetSkillsFragment();
        Bundle args = new Bundle();
        args.putString(CHAR_UUID, uuid);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String uuid = getArguments().getString(CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);

        return inflater.inflate(R.layout.fragment_character_sheet_skills, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView mAthletics = getView().findViewById(R.id.athletics_bonus);
        mAthletics.setText(String.format(Locale.US, "%d", mCurrentChar.getStrScore().getModifier() + assignBonus("Athletics")));

        TextView mAcrobatics = getView().findViewById(R.id.acrobatics_bonus);
        mAcrobatics.setText(String.format(Locale.US, "%d", mCurrentChar.getDexScore().getModifier() + assignBonus("Acrobatics")));

        TextView mSoH = getView().findViewById(R.id.sleight_of_hand_bonus);
        mSoH.setText(String.format(Locale.US, "%d", mCurrentChar.getDexScore().getModifier() + assignBonus("Sleight of Hand")));

        TextView mStealth = getView().findViewById(R.id.stealth_bonus);
        mStealth.setText(String.format(Locale.US, "%d", mCurrentChar.getDexScore().getModifier() + assignBonus("Stealth")));

        TextView mArcana = getView().findViewById(R.id.arcana_bonus);
        mArcana.setText(String.format(Locale.US, "%d", mCurrentChar.getIntScore().getModifier() + assignBonus("Arcana")));

        TextView mHistory = getView().findViewById(R.id.history_bonus);
        mHistory.setText(String.format(Locale.US, "%d", mCurrentChar.getIntScore().getModifier() + assignBonus("History")));

        TextView mInvestigation = getView().findViewById(R.id.investigation_bonus);
        mInvestigation.setText(String.format(Locale.US, "%d", mCurrentChar.getIntScore().getModifier() + assignBonus("Investigation")));

        TextView mNature = getView().findViewById(R.id.nature_bonus);
        mNature.setText(String.format(Locale.US, "%d", mCurrentChar.getIntScore().getModifier() + assignBonus("Nature")));

        TextView mReligion = getView().findViewById(R.id.religion_bonus);
        mReligion.setText(String.format(Locale.US, "%d", mCurrentChar.getIntScore().getModifier() + assignBonus("Religion")));

        TextView mAnimHandling = getView().findViewById(R.id.animal_handling_bonus);
        mAnimHandling.setText(String.format(Locale.US, "%d", mCurrentChar.getWisScore().getModifier() + assignBonus("Animal Handling")));

        TextView mInsight = getView().findViewById(R.id.insight_bonus);
        mInsight.setText(String.format(Locale.US, "%d", mCurrentChar.getWisScore().getModifier() + assignBonus("Insight")));

        TextView mMedicine = getView().findViewById(R.id.medicine_bonus);
        mMedicine.setText(String.format(Locale.US, "%d", mCurrentChar.getWisScore().getModifier() + assignBonus("Medicine")));

        TextView mPerception = getView().findViewById(R.id.perception_bonus);
        mPerception.setText(String.format(Locale.US, "%d", mCurrentChar.getWisScore().getModifier() + assignBonus("Perception")));

        TextView mSurvival = getView().findViewById(R.id.survival_bonus);
        mSurvival.setText(String.format(Locale.US, "%d", mCurrentChar.getWisScore().getModifier() + assignBonus("Survival")));

        TextView mDeception = getView().findViewById(R.id.deception_bonus);
        mDeception.setText(String.format(Locale.US, "%d", mCurrentChar.getChaScore().getModifier() + assignBonus("Deception")));

        TextView mIntimidation = getView().findViewById(R.id.intimidation_bonus);
        mIntimidation.setText(String.format(Locale.US, "%d", mCurrentChar.getChaScore().getModifier() + assignBonus("Intimidation")));

        TextView mPerformance = getView().findViewById(R.id.performance_bonus);
        mPerformance.setText(String.format(Locale.US, "%d", mCurrentChar.getChaScore().getModifier() + assignBonus("Performance")));

        TextView mPersuasion = getView().findViewById(R.id.persuasion_bonus);
        mPersuasion.setText(String.format(Locale.US, "%d", mCurrentChar.getChaScore().getModifier() + assignBonus("Persuasion")));
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
