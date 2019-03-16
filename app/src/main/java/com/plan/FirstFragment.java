package com.plan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xy.plan.R;
import com.githang.groundrecycleradapter.GroupItemDecoration;
import com.githang.groundrecycleradapter.GroupRecyclerAdapter;
import com.githang.groundrecycleradapter.OnChildClickListener;
import com.githang.groundrecycleradapter.OnGroupClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    private static final int VIEWTYPE_TWO = 1;
    private static final int VIEWTYPE_THREE = 2;
    private static final int VIEWTYPE_OTHER = 3;
    int viewType;
    int height = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view  = inflater.inflate(R.layout.layout_first, container,false);
        initData();
        return view;
    }

    protected void initData() {
        List<Plan_Level> plan_levels = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            List<Plan_member> plan_members = new ArrayList<>();
            String planlevel = "T"+i;
            for (int j = 0; j < 2; j++) {
                plan_members.add(new Plan_member("计划名称" + j, "计划类型" + j,"计划时间",66,66));
            }
            plan_levels.add(new Plan_Level(planlevel, plan_members));

        }


        final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        SwipeRecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setOnItemMenuClickListener(mItemMenuClickListener);

        final GroupRecyclerAdapter<Plan_Level, Plan_LevelViewHolder, Plan_memberViewHolder> recyclerAdapter =
                new GroupRecyclerAdapter<Plan_Level, Plan_LevelViewHolder, Plan_memberViewHolder>(plan_levels) {
                    @Override
                    protected Plan_LevelViewHolder onCreateGroupViewHolder(ViewGroup parent) {
                        viewType = VIEWTYPE_THREE;
                        return new Plan_LevelViewHolder(layoutInflater.inflate(R.layout.level, parent, false));
                    }

                    @Override
                    protected Plan_memberViewHolder onCreateChildViewHolder(ViewGroup parent) {
                        viewType = VIEWTYPE_TWO;
                        return new Plan_memberViewHolder(layoutInflater.inflate(R.layout.plan_item, parent, false));
                    }

                    @Override
                    protected void onBindGroupViewHolder(Plan_LevelViewHolder holder, int groupPosition) {
                        holder.update(getGroup(groupPosition));
                    }

                    @Override
                    protected void onBindChildViewHolder(Plan_memberViewHolder holder, int groupPosition, int childPosition) {
                        holder.update(getGroup(groupPosition).members.get(childPosition));
                    }

                    @Override
                    protected int getChildCount(Plan_Level group) {
                        return group.members.size();
                    }
                };
        recyclerView.setAdapter(recyclerAdapter);

        GroupItemDecoration decoration = new GroupItemDecoration(recyclerAdapter);
        decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
        decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_px, null));
        decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
        recyclerView.addItemDecoration(decoration);

        recyclerAdapter.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public void onGroupItemClick(View itemView, int groupPosition) {
                showToast(recyclerAdapter.getGroup(groupPosition).level);
            }
        });
        recyclerAdapter.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public void onChildClick(View itemView, int groupPosition, int childPosition) {
                Intent intent = new Intent(getActivity(),set.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            if(position != 0 && viewType == VIEWTYPE_TWO)
                height = ViewGroup.LayoutParams.MATCH_PARENT;
            else if(position == 0 || viewType == VIEWTYPE_THREE){
                height = 0;
                width = 0;
            }

            // 1. 根据ViewType来决定哪一个item该如何添加菜单。
            // 2. 更多的开发者需要的是根据position，因为不同的ViewType之间不会有缓存优化效果。
            if (viewType == VIEWTYPE_TWO) {
                SwipeMenuItem closeItem = new SwipeMenuItem(getContext()).setBackground(
                        R.drawable.selector_purple).setImage(R.drawable.ic_action_close).setWidth(width).setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。
                SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(
                        R.drawable.selector_green)
                        .setText("完成")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };


    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

