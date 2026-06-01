package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameTime;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {

    //判定框
    private Rectangle rect;

    //hp
    public int hp = 1;

    //Boob 效果判定
    public boolean IsInvincible = false;
    public int shield = 0;
    public long invincibleTime = 0;
    public long nowtime = 0;


    private BufferedImage[] images;
    private BufferedImage[] Booms;
    private BufferedImage[] Boobs;
    public static final int BIRD_IMG_COUNT = 3;

    //state of bird
    private int state;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_UP = 1;
    public static final int STATE_DOWN = 2;

    //location
    private int x = 300,y = 330;
    //move direction
    private boolean up = false;

    //move velocity
    private int upspeed = 5;
    private int downspeed = 1;

    public Bird(){
        images = new BufferedImage[BIRD_IMG_COUNT];
        for (int i = 0; i < BIRD_IMG_COUNT; i++){
            images[i] = GameUtil.loadBufferedImage(Constant.BIRD_IMG[i]);
        }

        Booms = new BufferedImage[8];
        for (int i = 0; i < 8; i++){
            Booms[i] = GameUtil.loadBufferedImage(Constant.BOOM_IMG[i]);
        }

        Boobs = new BufferedImage[3];
        for (int i = 0; i < 3; i++){
            Boobs[i] = GameUtil.loadBufferedImage(Constant.BOOB_IMG[i]);
        }

        int w = images[0].getWidth();
        int h = images[0].getHeight();
        rect = new Rectangle(w-10,h-10);

    }

    public void draw(Graphics g){
        flyLogic();
        g.drawImage(images[state],x,y,null);
        ShowingLogic(g);

        //判定框
//        g.drawRect(x,y,(int)rect.getWidth(),rect.height);
        rect.x = this.x ;
        rect.y = this.y ;
    }

    // control move direction
    public void flyLogic(){
        nowtime = GameTime.getInstance().getNowTime();

        if(nowtime - invincibleTime > 8000){
            IsInvincible = false;
        }
        if(up){
            y-=upspeed;
            downspeed = 1;
            if(y<20){
                y=20;
            }
            upspeed += 1;
        }
        if( ! up){
            upspeed = 5;
            y+=downspeed;
            if(y>Constant.FRAM_HEIGHT-30){
                y=Constant.FRAM_HEIGHT-30;
            }
            downspeed += 1;
        }
    }

    public void ShowingLogic(Graphics g){
        if (IsInvincible && !hasShield()){
            g.drawImage(Boobs[1],x-5,y-10,null);
        }else if (hasShield() && !IsInvincible){
            g.drawImage(Boobs[0],x-5,y-10,null);;
        }else if(IsInvincible && hasShield()){
            g.drawImage(Boobs[2],x-5,y-10,null);
        }
    }

    public void fly(int fly){
        switch (fly){
            case 1:
                state = 1;
                up = true;
                break;
            case 5:
                state = 2;
                up = false;
                break;
        }
    }

    public Rectangle getRect(){
        return rect;
    }

    public void restart(){
        hp = 1;
        x = 300;
        y = 330;
    }

    public void die(Graphics2D g,int timer){
        if(timer <= 7 ) {
            g.drawImage(Booms[timer], x-5, y-5, null);
        } else {

        }

    }


    public void setHP(int newHP){
        this.hp = newHP;
    }

    public void getInvincible(){
        IsInvincible = true;
        invincibleTime = GameTime.getInstance().getNowTime();
    }

    public void getShield(){
        shield++;
    }

    public boolean hasShield(){
        if(shield > 0){
            return true;
        }
        return false;
    }

    public BufferedImage getBoobImage(int i){
        return Boobs[i];
    }
}
