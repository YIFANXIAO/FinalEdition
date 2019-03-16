package com.tomatobell.Function;

/**
 * Created by 大松鼠 on 2018/12/28.
 */

public abstract class TomatoBell {
    //番茄钟模式
    private boolean islock=false;
    //工作时长
    private int concentrateDuration=0;
    //休息时长
    private int restDuration=0;

    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }


}
