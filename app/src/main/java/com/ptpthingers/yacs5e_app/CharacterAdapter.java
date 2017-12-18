package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

import java.util.List;

/**
 * Created by leo on 16.11.17.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    public static final String CHAR_UUID = "character_uuid";
    private static List<String> mUuidList;

    public CharacterAdapter(List<String> uuidList) {
        mUuidList = uuidList;
    }

    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext())
                .inflate(R.layout.character_thumb, viewGroup, false);

        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CharacterViewHolder viewHolder, final int position) {
        Character character = new Gson().fromJson(DBWrapper.getCharEntity(mUuidList.get(position)).getData(), Character.class);
        viewHolder.mCharacterName.setText(character.getCharName() + position);
        viewHolder.mCharacterDesc.setText(character.getShortDesc());

        viewHolder.mCharacterDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), viewHolder.mCharacterDelete);

                popup.inflate(R.menu.chracater_list_item_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        final String removedUuid = mUuidList.get(position);
                        if (menuItem.getItemId() == R.id.delete_character) {
                            Log.d("Hnnn", "onMenuItemClick: "+ mUuidList.size());
                            DBWrapper.setToDelete(removedUuid);
                            Log.d("Hnnn", "onMenuItemClick: "+ mUuidList.size());
                            CharacterListFragment.deleteItem(position);
                            Snackbar.make(view, "Character deleted!", Snackbar.LENGTH_LONG).setAction("UNDO",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view1) {
                                            Log.d("Hnnn", "onMenuItemClick: "+ mUuidList.size());
                                            DBWrapper.unsetToDelete(removedUuid);
                                            Log.d("Hnnn", "onMenuItemClick: "+ mUuidList.size());
                                            CharacterListFragment.addItem(position, removedUuid);
                                            Snackbar.make(view1, "Character restored", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }).show();
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUuidList.size();
    }

    // ViewHolder Implementation.
    // Assign Name, ShortDesc, portrait and favourite status to the CharacterThumb Card
    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;

        private final ImageView mCharacterPortrait;
        private final TextView mCharacterName;
        private final TextView mCharacterDesc;
        private final ImageButton mCharacterDelete;

        public CharacterViewHolder(final View v) {
            super(v);
            mContext = v.getContext();
            mCharacterPortrait = (ImageView) v.findViewById(R.id.thumb_image);
            mCharacterName = (TextView) v.findViewById(R.id.thumb_name);
            mCharacterDesc = (TextView) v.findViewById(R.id.thumb_desc);
            mCharacterDelete = (ImageButton) v.findViewById(R.id.thumb_fav);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent characterSheet = new Intent(mContext, CharacterSheetActivity.class);
                    characterSheet.putExtra(CHAR_UUID, mUuidList.get(getAdapterPosition()));
                    mContext.startActivity(characterSheet);
                }
            });
        }
    }

}
