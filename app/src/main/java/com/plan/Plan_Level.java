package com.plan;

import java.util.List;

public class Plan_Level {
    public final String level;
    public final List<Plan_member> members;

    public Plan_Level(String level, List<Plan_member> members) {
        this.level = level;
        this.members = members;
    }
}
