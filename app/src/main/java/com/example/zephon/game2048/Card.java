package com.example.zephon.game2048;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    public Card(Context context) {
        super(context);
        //动态添加TextView
        label = new TextView(getContext());
        //设置TextView的相关属性
        label.setTextSize(42);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        label.setTextColor(0xff776e65);
        label.setTypeface(Typeface.DEFAULT_BOLD);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(label,lp);

        setNum(0);
    }

    private int num=0;

    public void setNum(int num) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else{
            label.setText(num+"");
        }
    }

    public int getNum() {
        return num;
    }

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    private TextView label;
}
