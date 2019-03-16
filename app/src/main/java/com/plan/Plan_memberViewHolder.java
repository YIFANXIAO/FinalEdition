package com.plan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xy.plan.R;

public class Plan_memberViewHolder extends RecyclerView.ViewHolder {

    private final TextView mNameView;
    private final TextView mTypeView;
    private final TextView mTimeView;
    private final TextView mNumView;
    private final ProgressBar mProgress;

    Plan_memberViewHolder(View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.name);
        mTypeView = itemView.findViewById(R.id.type);
        mTimeView = itemView.findViewById(R.id.time);
        mNumView = itemView.findViewById(R.id.progesss_value1);
        mProgress = itemView.findViewById(R.id.progesss1);
    }

    void update(Plan_member member) {
        mNameView.setText(member.planName);
        mTypeView.setText(member.planType);
        mTimeView.setText(member.planTime);
        mNumView.setText(member.planNum+"%");
        mProgress.setProgress(member.planProgress);
    }
}