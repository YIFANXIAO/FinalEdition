package com.plan;

import android.widget.ProgressBar;

public class Plan_member {
    public final String planName;
    public final String planType;
    public final String planTime;
    public final int planNum;
    public final int planProgress;

    public Plan_member(String planName,String planType,String planTime,int planNum,int planProgress) {
        this.planName = planName;
        this.planType = planType;
        this.planTime = planTime;
        this.planNum = planNum;
        this.planProgress = planProgress;
    }
}
