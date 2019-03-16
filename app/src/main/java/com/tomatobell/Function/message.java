package com.tomatobell.Function;

import android.os.Message;

/**
 * Created by 大松鼠 on 2019/2/23.
 */
//获取倒计时显示信息
public interface message {
    public Message getMessage(int countDown);

    //转换文本框中的数字为int
    public int toInt(String input);
}


