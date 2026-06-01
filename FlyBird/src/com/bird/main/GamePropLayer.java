package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePropLayer {
    //random number produce
    private Random random;

    private List<FlyingProp> props;
    private int PROP_COUNT = 2;

    private  int PROP_SPEED ;
    private BufferedImage[] img ;

    //timer
    private int propTimer;
    private static final int PROP_GENERATE_INTERVAL = 100;



    public GamePropLayer(){
        props = new ArrayList<>();
        random = new Random();
        img = new BufferedImage[PROP_COUNT];

        for(int i = 0; i< 2 ; i++){
            img[i] = GameUtil.loadBufferedImage( Constant.PROP_IMG [i]);
        }
    }

    //draw cloud
    public void draw(Graphics g, Bird bird){
        logic(bird);

        for(int i = 0; i < props.size(); i++){
            props.get(i).draw(g);
        }
    }


    private  void logic(Bird bird){
        propTimer ++;
        PROP_SPEED = random.nextInt(8,14);

        int thisRandom = random.nextInt(100);
        int propType ;
        if( thisRandom < 30 ){
            propType = 1;
        } else  {
            propType = 0;
        }

        if( propTimer >= PROP_GENERATE_INTERVAL + random.nextInt(60)){
            FlyingProp prop = new FlyingProp(Constant.FRAM_WIDTH, random.nextInt(Constant.FRAM_HEIGHT), PROP_SPEED,img[propType]);
            if ( propType == 1 ){
                prop.setPropertyStrategy(new InvincibleProp());
                System.out.println("the next boob is Invincible");
            } else if ( propType == 0 ) {
                prop.setPropertyStrategy(new ShieldProp());
                System.out.println("the next boob is Shield");
            }
            props.add(prop);
            propTimer = 0;
        }
        for (int i = 0; i< props.size(); i++){
            FlyingProp prop = props.get(i);
            if(prop.IsGetProp(bird)){
                prop.PropEffect(bird);
                props.remove(i);
                i--;
            } else if(prop.IsInvalid(bird)){
                props.remove(i);
                i--;
            }

        }
    }

}
