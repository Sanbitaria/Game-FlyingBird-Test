package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBackGround {
    // store
    private BufferedImage bkimg;

    public GameBackGround(){
        bkimg = GameUtil.loadBufferedImage(Constant.BK_IMG_PATH);
    }

    //draw
    public void draw(Graphics g){

        //BK_Color
        g.setColor(Constant.BK_COLOR);
        g.fillRect(0,0,Constant.FRAM_WIDTH,Constant.FRAM_HEIGHT);
        g.setColor(Color.BLACK);
        //get
        int h = bkimg.getHeight();
        int w = bkimg.getWidth();

        //recycle display
        int count = Constant.FRAM_WIDTH / w + 1;

        for (int i = 0; i < count; i++){
            g.drawImage(bkimg,w*i,Constant.FRAM_HEIGHT-h,null);

        }


    }
}
