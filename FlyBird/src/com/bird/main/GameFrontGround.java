package com.bird.main;

import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrontGround {
    //random number produce
    private Random random;

    //amount of cloud
    private static final int CLOUD_COUNT = 6;
    //cloud container
    private List<Cloud> clouds;
    //speed of cloud
    private  int CLOUD_SPEED ;
    private BufferedImage[] img ;

    //timer
    private int cloudTimer;
    private static final int CLOUD_GENERATE_INTERVAL = 180;



    public GameFrontGround(){
        clouds = new ArrayList<>();
        img = new BufferedImage[CLOUD_COUNT];

        //add picture
        for(int i = 0; i< CLOUD_COUNT ; i++){
            int count = i + 1 ;
            img[i] = GameUtil.loadBufferedImage("img/cloud"+count+".png");
        }
        random = new Random();

    }

    //draw cloud
    public void draw(Graphics g){
        logic();

        for(int i = 0; i < clouds.size(); i++){
            clouds.get(i).draw(g);
        }
    }

    //to control the count of cloud
    private  void logic(){
        cloudTimer ++;
        CLOUD_SPEED = random.nextInt(2,5);

        if( cloudTimer >= CLOUD_GENERATE_INTERVAL + random.nextInt(60)){
            Cloud cloud = new Cloud(1100, random.nextInt(250), CLOUD_SPEED,img[random.nextInt(CLOUD_COUNT)]);
            clouds.add(cloud);
            cloudTimer = 0;
        }
        for (int i = 0; i< clouds.size(); i++){
            Cloud cloud = clouds.get(i);
            if(cloud.isOutFrame()){
                clouds.remove(i);
                i--;
                System.out.println("Cloud has been removed"+cloud);
            }

        }
    }
}
