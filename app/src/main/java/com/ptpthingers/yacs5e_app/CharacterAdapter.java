package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by leo on 16.11.17.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private static List<Character> mCharacterList;

    public CharacterAdapter(List<Character> characterList) {
        mCharacterList = characterList;
    }

    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext())
                .inflate(R.layout.character_thumb, viewGroup, false);

        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder viewHolder, int position) {
        Character character = mCharacterList.get(position);
        viewHolder.mCharacterName.setText(character.getCharName());
        viewHolder.mCharacterDesc.setText(character.getShortDesc());
        viewHolder.mFavButton.setEnabled(character.getFavourite());
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    // ViewHolder Implementation.
    // Assign Name, ShortDesc, portrait and favourite status to the CharacterThumb Card
    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;

        private final ImageView mCharacterPortrait;
        private final TextView mCharacterName;
        private final TextView mCharacterDesc;
        private final ImageButton mFavButton;

        public CharacterViewHolder(final View v) {
            super(v);
            mContext = v.getContext();
            mCharacterPortrait = (ImageView) v.findViewById(R.id.thumb_image);
            mCharacterName = (TextView) v.findViewById(R.id.thumb_name);
            mCharacterDesc = (TextView) v.findViewById(R.id.thumb_desc);
            mFavButton = (ImageButton) v.findViewById(R.id.thumb_fav);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    builder.serializeNulls();
                    Intent characterSheet = new Intent(mContext, CharacterSheetActivity.class);
                    characterSheet.putExtra("CHARACTER", gson.toJson(mCharacterList.get(getAdapterPosition())));
                    mContext.startActivity(characterSheet);
                }
            });
        }
    }

}
