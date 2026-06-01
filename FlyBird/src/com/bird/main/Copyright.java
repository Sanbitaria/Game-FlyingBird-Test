package com.bird.main;

import java.awt.*;

public class Copyright {
    private static Copyright copyright = null;
    private String COPYRIGHT_INFORM = "©COPYRIGHT: JAVA-OBJECT-ORENTED-PROGRAMING" ;

    public static Copyright getInstance(){
        if(copyright == null){
            copyright = new Copyright();
        }
        return copyright;
    }

    public void draw(Graphics2D g){

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",2,15));
        g.drawString( COPYRIGHT_INFORM,20,50 );
    }


}