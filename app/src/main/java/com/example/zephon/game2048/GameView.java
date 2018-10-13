package com.example.zephon.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    private void initGameView(){

        setBackgroundColor(0xffbbada0);
        setColumnCount(4);
        //手势操作
        setOnTouchListener(new View.OnTouchListener() {
            private float startX,startY,offsetX,offSetY;//offsetX/Y 偏移量
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX()-startX;
                        offSetY = event.getY()-startY;

                        if(Math.abs(offsetX)>Math.abs(offSetY)){
                            if(offsetX<-5){
                                swipeLeft();
                            }else if(offsetX>5){
                                //right
                                swipeRight();
                            }
                        }else{
                            if(offSetY<-5){
                                //up
                                swipeUp();
                            }else if(offSetY>5){
                                //down
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h)-10)/4;//得出每一张卡片宽度

        addCards(cardWidth,cardWidth);

        startGame();

    }

    private void addCards(int cardWidth,int cardHeight){

        Card c;
        for(int y=0;y<4;y++){
            for(int x =0;x<4;x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardMap[x][y] = c;
            }
        }
    }

    private void startGame(){

        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                cardMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();

    }

    private void addRandomNum(){

        emptyPoints.clear();

        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                if(cardMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardMap[p.x][p.y].setNum(Math.random()>=0.1?2:4);
    }



    private void swipeLeft(){

        boolean merge = false;

        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){

                for(int i=x+1;i<4;i++){
                    if(cardMap[i][y].getNum()>0){
                        if(cardMap[x][y].getNum()<=0){
                            cardMap[x][y].setNum(cardMap[i][y].getNum());
                            cardMap[i][y].setNum(0);
                            merge = true;
                        }else if(cardMap[x][y].equals(cardMap[i][y])){
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
                            cardMap[i][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }

            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge = false;
        for(int y=0;y<4;y++){
            for(int x=3;x>=0;x--){

                for(int i=x-1;i>=0;i--){
                    if(cardMap[i][y].getNum()>0){
                        if(cardMap[x][y].getNum()<=0){
                            cardMap[x][y].setNum(cardMap[i][y].getNum());
                            cardMap[i][y].setNum(0);

                            merge = true;
                        }else if(cardMap[x][y].equals(cardMap[i][y])){
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
                            cardMap[i][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }

            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean merge = false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){

                for(int j=y+1;j<4;j++){
                    if(cardMap[x][j].getNum()>0){
                        if(cardMap[x][y].getNum()<=0){
                            cardMap[x][y].setNum(cardMap[x][j].getNum());
                            cardMap[x][j].setNum(0);
                            merge = true;
                        }else if(cardMap[x][y].equals(cardMap[x][j])){
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
                            cardMap[x][j].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }

            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean merge = false;
        for(int x=0;x<4;x++){
            for(int y=3;y>=0;y--){

                for(int j=y-1;j>=0;j--){
                    if(cardMap[x][j].getNum()>0){
                        if(cardMap[x][y].getNum()<=0){
                            cardMap[x][y].setNum(cardMap[x][j].getNum());
                            cardMap[x][j].setNum(0);
                            merge = true;
                        }else if(cardMap[x][y].equals(cardMap[x][j])){
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
                            cardMap[x][j].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }

            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete(){
        boolean complete = true;
        ALL:
        for(int y = 0;y<4;y++){
            for(int x=0;x<4;x++){
                if((cardMap[x][y].getNum()==0)||
                        (x>0&&(cardMap[x][y].equals(cardMap[x-1][y])))||
                        (x<3&&(cardMap[x][y].equals(cardMap[x+1][y])))||
                        (y>0&&(cardMap[x][y].equals(cardMap[x][y-1])))||
                        (y<3&&(cardMap[x][y].equals(cardMap[x][y+1])))){
                    complete = false;
                    break ALL;
                }
            }
        }
        if(complete){

            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).setCancelable(false).show();
        }
    }

    private Card[][] cardMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

}
