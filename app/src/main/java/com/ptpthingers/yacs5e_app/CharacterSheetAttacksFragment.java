package com.ptpthingers.yacs5e_app;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterSheetAttacksFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static AttacksAdapter mAdapter;
    private static LinkedList<Attack> mAttackList;
    private static Character mCurrentChar;

    public CharacterSheetAttacksFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetAbilityScoresFragment newInstance(String uuid) {
        CharacterSheetAbilityScoresFragment fragment = new CharacterSheetAbilityScoresFragment();
        Bundle args = new Bundle();
        args.putString(CHAR_UUID, uuid);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttackList = new LinkedList<>();
        String uuid = getArguments().getString(CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        try {
            mAttackList.addAll(mCurrentChar.getmAttacks());
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_sheet_attacks, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.attacks_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AttacksAdapter(mRecyclerView, mAttackList);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_attack);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttackList.add(new Attack());
                mAdapter.notifyItemInserted(mAttackList.size());
                mCurrentChar.setmAttacks(mAttackList);
                mCurrentChar.post(getContext().getSharedPreferences("account", Context.MODE_PRIVATE).getString("username", ""));
            }
        });

        return rootView;
    }

}
