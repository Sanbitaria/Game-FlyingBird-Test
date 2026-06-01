package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Barrier {

    //collision box
    private Rectangle rect;
    private boolean disRect = false;

    private Random random = new Random();
    //vocation
    public int speed = 5;
    //barrier img
    private static BufferedImage[] imgs;


    //barrier dead or alive

    public int getSpeed(){return speed;}

    public void setSpeed(int speed) {this.speed = speed;}

    private boolean visible ;
    static {
        final int COUNT_BARRIER = 3;

        imgs = new BufferedImage[COUNT_BARRIER];
        for(int i = 0 ; i < COUNT_BARRIER; i++){
            imgs[i] = GameUtil.loadBufferedImage(Constant.BARRIER_IMG[i]);
        }
    }

    //location
    private int x,y;
    private int width,height;
    private int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_MIDDLE = 4;
    public static final int TYPE_MOVE = 6;

    public static final int BARRIER_WIDTH = imgs[0].getWidth();
    public static final int BARRIER_HEIGHT = imgs[0].getHeight();
    public static final int BARRIER_HEAD_WIDTH = imgs[1].getWidth();
    public static final int BARRIER_HEAD_HEIGHT = imgs[1].getHeight();

    public Barrier(){
        rect = new Rectangle();
    }

    public Barrier (int x ,int y ,int height , int type,int speed){
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.speed = speed;

        this.width = BARRIER_WIDTH;



    }

    //recording different type draw different barrier
    public void drawBarrier (Graphics g){
        switch (type){
            case TYPE_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawNormalTop(g);
                break;
            case TYPE_MIDDLE:
                drawMiddle(g);
                break;
            case TYPE_MOVE:
                drawMOVE(g);
                break;
        }
    }


    //draw up to down
    private void drawTopNormal(Graphics g){
        int count = (height - BARRIER_HEAD_HEIGHT)/BARRIER_HEIGHT + 1;

        for(int i = 0 ; i< count ; i++){
            g.drawImage(imgs[0],x,y+ i*BARRIER_HEIGHT,null);
        }

        int y2 = height - BARRIER_HEAD_HEIGHT;
        g.drawImage(imgs[2],x-(BARRIER_HEAD_WIDTH-BARRIER_WIDTH)/2, y2,null);
        x -= speed;
        if (x < -50){
            visible = false;
        }
        rect(g);
    }

    //draw down to up

    private void drawNormalTop(Graphics g){
        int count = height /BARRIER_HEIGHT +1;

        for(int i = 0 ; i < count ; i++){
            g.drawImage(imgs[0],x,Constant.FRAM_HEIGHT-i*BARRIER_HEIGHT,null);
        }

        int y2 = Constant.FRAM_HEIGHT-height;
        g.drawImage(imgs[1],x-(BARRIER_HEAD_WIDTH-BARRIER_WIDTH)/2, y2,null);
        x -= speed;

        if (x < -50){
            visible = false;
        }

        rect(g);
    }

    // middle type barrier
    private void drawMiddle(Graphics g){
        int count = (height - BARRIER_HEAD_HEIGHT)/BARRIER_HEIGHT ;

        g.drawImage(imgs[1],x,y,null);

        for(int i = 0 ; i < count ; i++){
            g.drawImage(imgs[0],x,y+BARRIER_HEAD_HEIGHT+i*BARRIER_HEIGHT,null);
        }

        int y2 = y+height-BARRIER_HEAD_HEIGHT;
        g.drawImage(imgs[2],x, y2,null);
        x -= speed;

        if (x < -50){
            visible = false;
        }

        rect(g);
    }


    private boolean mob = true;
    // middle move type
    private void drawMOVE(Graphics g){
        int count = (height - BARRIER_HEAD_HEIGHT)/BARRIER_HEIGHT ;

        g.drawImage(imgs[1],x,y,null);

        for(int i = 0 ; i < count ; i++){
            g.drawImage(imgs[0],x,y+BARRIER_HEAD_HEIGHT+i*BARRIER_HEIGHT,null);
        }

        int y2 = y+height-BARRIER_HEAD_HEIGHT;
        g.drawImage(imgs[2],x, y2,null);
        x -= speed;

        if (x < -50){
            visible = false;
        }

        if(mob){
            y += 5;
            if(y >= 250){
                mob = false;
            }
        } else if(!mob){
            y -= 5;
            if(y <= 100){
                mob = true;
            }
        }

        rect(g);
    }


    //draw collision box
    public void rect(Graphics g){
        int x1 = this.x;
        int y1 = this.y;
        int w1 = imgs[0].getWidth();
//        g.setColor(Color.red);
//        g.drawRect(x1,y1,w1,height);
        if(disRect){
            setRectangle(-999,-999,0,0);
        } else if (!disRect) {
            setRectangle(x1,y1,w1,height);
        }
    }

    //collision box parameter
    public void setRectangle(int x,int y,int width,int height){
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;

    }








    // when to draw the next group of barriers
    public boolean isInFrame(){
        return Constant.FRAM_WIDTH - x > 200 + random.nextInt(100);
    }

    //getter and setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getRect(){
        return rect;
    }

    public void removeRect(){
        disRect = true;
    }

    public void backRect(){
        disRect = false;
    }


}
