package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBarrierLayer {

    private GameTime gameTime;

    private Random random =new Random();

    private List<Barrier> barriers;

    private int speed = 5;

    public GameBarrierLayer() {

        barriers = new ArrayList<>();
        gameTime = GameTime.getInstance();
    }

    public void draw(Graphics2D g,Bird bird){
        for (int i = 0; i < barriers.size(); i++){
            Barrier barrier = barriers.get(i);
            if (barrier.isVisible()){
                barrier.drawBarrier(g);
            } else {
                Barrier remove = barriers.remove(i);
                Barrierpool.setPool(remove);
                i--;
            }

        }
        collideBird(bird);
        logic(g);
    }

    public void logic(Graphics2D g){
        if ( barriers.isEmpty() ){
            ran();
            gameTime.begin();
            insert(Constant.FRAM_WIDTH,0,numberTop,0,speed);
            insert(Constant.FRAM_WIDTH,Constant.FRAM_HEIGHT-numberBottom,numberBottom, 2,  speed);
        }else {

            long differ = gameTime.differ();
            g.setColor(Color.white);
            g.setFont(new Font("Arial",1,30));
            g.drawString("Time/Score: " + differ,20,80);

            Barrier last = barriers.get(barriers.size()-1);


            if(last.isInFrame()){
                ran();
                if (TypeNumber() == 1){
                    insert(Constant.FRAM_WIDTH,random.nextInt(60,200), random.nextInt(300,400), 4, speed);
                } else if(TypeNumber() == 2){

                    insert(Constant.FRAM_WIDTH, random.nextInt(100,200), random.nextInt(300,350), 6, speed);
                } else if(TypeNumber() == 3){
                    insert(Constant.FRAM_WIDTH, 0, numberTop, 0, speed);
                    insert(Constant.FRAM_WIDTH, Constant.FRAM_HEIGHT - numberBottom, numberBottom, 2, speed);
                }
            }

            last.backRect();

        }


    }


    public void insert(int x, int y,int num,int type,int speed){
        Barrier top = Barrierpool.getPool();
        top.setX(x);
        top.setY(y);
        top.setHeight(num);
        top.setType(type);
        top.setVisible(true);
        top.backRect();
        top.setSpeed(speed);
        barriers.add(top);
    }

    private int numberTop;
    private int numberBottom;

    private int numberType;

    public void ran(){
        numberTop = random.nextInt(100,400);
        numberBottom = Constant.FRAM_HEIGHT - numberTop - random.nextInt(125,200);
        numberType = random.nextInt(500);
    }

    public int TypeNumber(){
        int type = 3;
        if(numberType < 50){
            type = 1; //middle
        } else
        if(numberType > 450) {
            type = 2; // middle move
        } else {
            type = 3;
        }
        return type;
    }

    //collision box judge
    public boolean collideBird(Bird bird){
        for (int i = 0; i < barriers.size(); i++ ){
            Barrier barrier = barriers.get(i);
            if (BarrierKissBird(barrier,bird)){
                System.out.println("die");
                if(bird.IsInvincible){

                } else if(bird.shield>0){
                    bird.shield--;
                } else if (bird.shield == 0) {
                    bird.hp--;
                }
                barrier.removeRect();
            }
        }
        return false;
    }

    public void restart(){
        barriers.clear();
    }

    public boolean BarrierKissBird(Barrier barrier,Bird bird){
        if(barrier.getRect().intersects(bird.getRect())){
            return true;
        }
        return false;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
