package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptpthingers.synchronization.DBWrapper;

import java.util.LinkedList;

public class CharacterListFragment extends Fragment {

    public static final String CHARACTER_LIST = "character_list";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mCurrentVisiblePosition;
    private static CharacterAdapter mAdapter;
    private static LinkedList<String> mCharacterList;
    private SharedPreferences accountSharedPreferences;


    private OnFragmentInteractionListener mListener;

    public CharacterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentVisiblePosition = 0;
        accountSharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        mCharacterList = new LinkedList<>();
        try {
            String username = accountSharedPreferences.getString("username", "");
            mCharacterList.addAll(DBWrapper.getUuidList(username));
        } catch (NullPointerException npe) {
            TextView tv = getActivity().findViewById(R.id.empty_list_text);
            tv.setText("No characters yet!\nTap the plus icon to create!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.character_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CharacterAdapter(mCharacterList);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = accountSharedPreferences.getString("username", "");
                mCharacterList.add(new Character().post(username));
                mAdapter.notifyItemInserted(mCharacterList.size());
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentVisiblePosition = 0;
        mCurrentVisiblePosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPosition(mCurrentVisiblePosition);
        mCurrentVisiblePosition = 0;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void deleteItem(int position) {
        mCharacterList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public static void addItem(int position, String item) {
        mCharacterList.add(position, item);
        mAdapter.notifyItemInserted(position);
    }
}
