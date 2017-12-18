package com.ptpthingers.yacs5e_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterSheetTraitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterSheetTraitsFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static AttacksAdapter mAdapter;
    private static LinkedList<Attack> mAttackList;
    private static Character mCurrentChar;


    public CharacterSheetTraitsFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetTraitsFragment newInstance() {
        CharacterSheetTraitsFragment fragment = new CharacterSheetTraitsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_sheet_traits, container, false);
    }

}
