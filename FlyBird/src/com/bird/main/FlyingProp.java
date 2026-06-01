package com.bird.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlyingProp {

    private Rectangle rectangle;
    private BufferedImage img;

    //location
    private int x;
    private int y;
    private int speed;

    public FlyingProp(int x, int y, int speed,BufferedImage img){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.img = img;

        int w = img.getWidth();
        int h = img.getHeight();
        rectangle = new Rectangle(w-10,h-10);
    }

    public void draw(Graphics g){
        x -= speed;
        g.drawImage(img,x,y,null);

        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    private PropertyStrategy propertyStrategy;

    public void setPropertyStrategy( PropertyStrategy propertyStrategy){
        this.propertyStrategy = propertyStrategy;
    }

    public void PropEffect(Bird bird){
        if(propertyStrategy != null){
            propertyStrategy.PropEffect(bird);
        }
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public boolean IsInvalid(Bird bird){
        if( x < -200){
            return true;
        } else if(IsGetProp(bird)){
            return true;
        }
        return  false;
    }

    public boolean IsGetProp(Bird bird){
        if(rectangle.intersects(bird.getRect())){
            return true;
        }
        return false;
    }


}
