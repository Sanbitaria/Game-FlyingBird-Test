package com.bird.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cloud {
    //cloud image
    private BufferedImage img;
    //speed
    private int speed;
    //location
    private int  x,y;

    public Cloud(){}

    public Cloud(int x, int y, int speed, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.img = img;
    }

    public void draw(Graphics g){
        x-=speed;
        g.drawImage(img,x,y,null);
    }

    //make sure if the cloud run out of the screen
    public boolean isOutFrame (){
        if (x < -300){
            return true;
        }
        return false;
    }
}
