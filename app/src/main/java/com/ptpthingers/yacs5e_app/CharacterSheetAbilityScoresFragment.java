package com.ptpthingers.yacs5e_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.annotation.Nullable;


public class CharacterSheetAbilityScoresFragment extends Fragment {

    public static final String JSON_FILE = "json_file";

    Character mCurrentChar;
    TextView mStrScore, mStrMod, mStrSave;
    TextView mDexScore, mDexMod, mDexSave;
    TextView mConScore, mConMod, mConSave;
    TextView mIntScore, mIntMod, mIntSave;
    TextView mWisScore, mWisMod, mWisSave;
    TextView mChaScore, mChaMod, mChaSave;

    public CharacterSheetAbilityScoresFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetAbilityScoresFragment newInstance(String json) {
        CharacterSheetAbilityScoresFragment fragment = new CharacterSheetAbilityScoresFragment();
        Bundle bundle = new Bundle();
        bundle.putString(JSON_FILE, json);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Gson gson = new Gson();
        String json = getArguments().getString(JSON_FILE);
        mCurrentChar = gson.fromJson(json, Character.class);

        return inflater.inflate(R.layout.fragment_character_sheet_ability_scores, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mStrScore = getView().findViewById(R.id.strValue);
        mStrMod = getView().findViewById(R.id.strMod);
        mStrSave = getView().findViewById(R.id.strSave);
        mStrScore.setText(mCurrentChar.getStrScore().getValue().toString());
        mStrMod.setText(mCurrentChar.getStrScore().getModifier().toString());
        if (mCurrentChar.getStrSave().getProficient()) {
            mStrSave.setText(mCurrentChar.getStrScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mStrSave.setText(mCurrentChar.getStrScore().getModifier().toString());
        }
        mDexScore = getView().findViewById(R.id.dexValue);
        mDexMod = getView().findViewById(R.id.dexMod);
        mDexSave = getView().findViewById(R.id.dexSave);
        mDexScore.setText(mCurrentChar.getDexScore().getValue().toString());
        mDexMod.setText(mCurrentChar.getDexScore().getModifier().toString());
        if (mCurrentChar.getDexSave().getProficient()) {
            mDexSave.setText(mCurrentChar.getDexScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mDexSave.setText(mCurrentChar.getDexScore().getModifier().toString());
        }
        mConScore = getView().findViewById(R.id.conValue);
        mConMod = getView().findViewById(R.id.conMod);
        mConSave = getView().findViewById(R.id.conSave);
        mConScore.setText(mCurrentChar.getConScore().getValue().toString());
        mConMod.setText(mCurrentChar.getConScore().getModifier().toString());
        if (mCurrentChar.getConSave().getProficient()) {
            mConSave.setText(mCurrentChar.getConScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mConSave.setText(mCurrentChar.getConScore().getModifier().toString());
        }
        mIntScore = getView().findViewById(R.id.intValue);
        mIntMod = getView().findViewById(R.id.intMod);
        mIntSave = getView().findViewById(R.id.intSave);
        mIntScore.setText(mCurrentChar.getIntScore().getValue().toString());
        mIntMod.setText(mCurrentChar.getIntScore().getModifier().toString());
        if (mCurrentChar.getIntSave().getProficient()) {
            mIntSave.setText(mCurrentChar.getIntScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mIntSave.setText(mCurrentChar.getIntScore().getModifier().toString());
        }
        mWisScore = getView().findViewById(R.id.wisValue);
        mWisMod = getView().findViewById(R.id.wisMod);
        mWisSave = getView().findViewById(R.id.wisSave);
        mWisScore.setText(mCurrentChar.getWisScore().getValue().toString());
        mWisMod.setText(mCurrentChar.getWisScore().getModifier().toString());
        if (mCurrentChar.getWisSave().getProficient()) {
            mWisSave.setText(mCurrentChar.getWisScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mWisSave.setText(mCurrentChar.getWisScore().getModifier().toString());
        }
        mChaScore = getView().findViewById(R.id.chaValue);
        mChaMod = getView().findViewById(R.id.chaMod);
        mChaSave = getView().findViewById(R.id.chaSave);
        mChaScore.setText(mCurrentChar.getChaScore().getValue().toString());
        mChaMod.setText(mCurrentChar.getChaScore().getModifier().toString());
        if (mCurrentChar.getChaSave().getProficient()) {
            mChaSave.setText(mCurrentChar.getChaScore().getModifier() + mCurrentChar.getProfBonus());
        } else {
            mChaSave.setText(mCurrentChar.getChaScore().getModifier().toString());
        }
    }

}
