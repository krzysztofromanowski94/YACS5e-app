package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by leo on 17.12.17.
 */

public class AttacksAdapter extends RecyclerView.Adapter<AttacksAdapter.AttacksViewHolder> {
    private static List<Attack> mAttackList;

    public AttacksAdapter(RecyclerView recyclerView, List<Attack> attackList) {
        mAttackList = attackList;
    }

    @Override
    public void onBindViewHolder(AttacksViewHolder holder, int position) {
        holder.mAttackName.setText(mAttackList.get(position).getName());
        holder.mAttackBonus.setText(Integer.toString(mAttackList.get(position).getmToHit()));
        holder.mAttackDamage.setText(mAttackList.get(position).getmDiceCount()+"d"+mAttackList.get(position).getmDiceType()+"+"+mAttackList.get(position).getmFlatBonus());
        holder.mAttackDamageType.setText(mAttackList.get(position).getmDamageType().toString());
        holder.mAttackDamageType.setAllCaps(false);
        holder.mAttackRange.setText(mAttackList.get(position).getmRange());
        holder.mAttackAbility.setText(Integer.toString(mAttackList.get(position).getmAbility().getModifier()));
    }

    @Override
    public AttacksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext())
                .inflate(R.layout.attack_item, parent, false);

        return new AttacksViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mAttackList.size();
    }

    public class AttacksViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAttackName;
        private final TextView mAttackBonus;
        private final TextView mAttackDamage;
        private final TextView mAttackDamageType;
        private final TextView mAttackRange;
        private final TextView mAttackAbility;

        private final Context mContext;

        public AttacksViewHolder(final View v) {
            super(v);

            mContext = v.getContext();

            mAttackName = v.findViewById(R.id.attack_name);
            mAttackBonus = v.findViewById(R.id.attack_bonus);
            mAttackDamage = v.findViewById(R.id.attack_damage);
            mAttackDamageType = v.findViewById(R.id.attack_damage_type);
            mAttackRange = v.findViewById(R.id.attack_range);
            mAttackAbility = v.findViewById(R.id.attack_ability);
        }
    }
}
