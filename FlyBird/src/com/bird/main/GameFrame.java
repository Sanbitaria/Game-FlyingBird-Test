package com.bird.main;

import com.bird.util.GameTime;
import com.bird.util.ScoreHistory;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static com.bird.util.Constant.*;

public class GameFrame extends Frame {

    //判断条件一群
    private int maskDown = 0;
    private boolean IsNewHistory = false;
    private boolean IsResetHistory = false;
    private boolean IsFirstStart = true;
    private boolean IsCease = false;
    private int WantToBack = 0;
    private int WantToExit = 0;
    //game level

    private int speed = 5 ;


    private GameBackGround gameBackGround;

    private Bird bird;

    private GameBarrierLayer gameBarrierLayer;

    private GameFrontGround gameFrontGround;

    private ScoreHistory scoreHistory;

    private GamePropLayer gamePropLayer;

    GameTime gameTime = GameTime.getInstance();

    private Copyright copyright = Copyright.getInstance();

    //the picture to load picture(?)
    private BufferedImage buffimg = new BufferedImage(FRAM_WIDTH,FRAM_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);


    public GameFrame(){
        setVisible(true);
        setSize(FRAM_WIDTH,FRAM_HEIGHT);
        setTitle(FRAM_TITLE);
        setLocation(FRAM_X,FRAM_Y);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }
        });

        initGameimg();

        new run().start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                add(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                minu(e);
            }
        });

    }

    public void initGameimg(){

        gameBackGround = new GameBackGround();
        bird = new Bird();
        gameFrontGround = new GameFrontGround();
        gameBarrierLayer = new GameBarrierLayer();
        scoreHistory = new ScoreHistory();
        gamePropLayer = new GamePropLayer();
    }

    class run extends Thread{
        @Override
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //word flash detection
    private int flash = 0;
    private boolean FlashOver = false;
    private int Boom = 0;
    private int cc = 0;


    public void update(Graphics g){
        Graphics2D g2d = (Graphics2D) g;


        //flash word
        if(!FlashOver){
            flash += 6;
            if(flash >= 246){
                FlashOver = true;
            }
        } else if (FlashOver){
            flash -= 4;
            if(flash <= 75){
                FlashOver = false;
            }
        }
        Color transparentWord = new Color(255, 255, 255, flash);
        Color transparentShadow = new Color(0, 0, 0, flash);

        if (bird.hp > 0 && !IsFirstStart && !IsCease) {
            //正常
            Graphics2D graphics = buffimg.createGraphics();
            gameBackGround.draw(graphics);
            gameFrontGround.draw(graphics);
            gameBarrierLayer.draw(graphics,bird);
            gamePropLayer.draw(graphics,bird);
            bird.draw(graphics);

            if(bird.hasShield()){
                graphics.drawImage(bird.getBoobImage(0),22,95,50,50,null);
                graphics.setColor(Color.white);
                graphics.setFont(new Font("Arial",1,30));
                graphics.drawString("X" + bird.shield ,75,128);
            }

            if(bird.IsInvincible){
                graphics.drawImage(bird.getBoobImage(1),22,165,50,50,null);
                graphics.setColor(Color.white);
                graphics.setFont(new Font("Arial",1,30));
                graphics.drawString( String.format("%.3f", 8.000 + (float)(bird.invincibleTime - bird.nowtime) / 1000) ,88,200);
            }

            copyright.draw(graphics);
            graphics.dispose();
            g.drawImage(buffimg,0,0,null);

        } else if(bird.hp == 0 && !IsCease){
           //死后结算

            Graphics2D g2dBuf =  buffimg.createGraphics();

            //死亡特效
            if(Boom <= 7) {
                cc++;
                if(cc>=2){
                    Boom++;
                    cc = 0;
                }
                bird.die(g2dBuf, Boom);
            }else{
                Boom = 8;
                cc = 0;
            }

            //遮罩
            if ( maskDown <= 50 ) {
                Color transparentBlack = new Color(0, 0, 0, 5);
                g2dBuf.setColor(transparentBlack);
                g2dBuf.fillRect(0, 0, FRAM_WIDTH, FRAM_HEIGHT);
                maskDown ++;
            }

            //时间计算
            gameTime.end();
            long ThisTermScore = gameTime.differ();
            long HistoryScore = scoreHistory.getScore();
            if( HistoryScore < ThisTermScore){
                scoreHistory.setScore(ThisTermScore);
                IsNewHistory = true;
            }

            String SCORE = "SCORE: " + ThisTermScore + "        " +"HISTORY: "+ HistoryScore;
            String GAMEOVER = "GAME OVER";
            String TIP_RESTART = "Press \"SPACE\" to restart";

            g2dBuf.setColor(Color.white);
            g2dBuf.setFont(new Font("Arial",1,140));
            g2dBuf.drawString(GAMEOVER,120,FRAM_HEIGHT/2 + 50);


            //得分显示
            if( IsNewHistory && !IsResetHistory ){
                Color colorful = new Color(flash+5,255-flash,flash/4+flash/3,255);
                g2dBuf.setColor(colorful);
                g2dBuf.setFont(new Font("Arial", 2, 40));
                String NEWHISTORY = "!! NEWHISTORY: "+ ThisTermScore;
                g2dBuf.drawString(NEWHISTORY,345,FRAM_HEIGHT/2 + 150);
            } else if(!IsResetHistory) {
                g2dBuf.setFont(new Font("Arial", 1, 30));
                g2dBuf.drawString(SCORE, 350, FRAM_HEIGHT / 2 + 150);
            }


            //重开
            Color test = new Color(255, 255, flash, flash);
            g2dBuf.setColor(test);
            g2dBuf.setFont(new Font("Arial",1,40));
            g2dBuf.drawString(TIP_RESTART,320,FRAM_HEIGHT/2+270);

            g2dBuf.setColor(Color.white);
            g2dBuf.setFont(new Font("Arial",1,25));
            g2dBuf.drawString("Press Esc twice  goback Menu",350,FRAM_HEIGHT/2+225);

            //判定新历史记录
            if(IsResetHistory){
                g2dBuf.setFont(new Font("Arial",0,10));
                g2dBuf.drawString("Reset",FRAM_WIDTH/2 -10,FRAM_HEIGHT/2+285);
            }


            g.drawImage(buffimg, 0, 0, null);

        } else if (IsFirstStart && !IsCease) {
            //菜单界面

            Graphics2D g2dBuf =  buffimg.createGraphics();
            gameBackGround.draw(g2dBuf);
            gameFrontGround.draw(g2dBuf);
            copyright.draw(g2dBuf);

            //cover
            Color color1 = new Color(0, 0, 0, 56);
            g2dBuf.setColor(color1);
            g2dBuf.fillRect(0, 0, FRAM_WIDTH, FRAM_HEIGHT);

            //titel shadow
            Color color2 = new Color(0, 100, 100, flash);
            g2dBuf.setColor(color2);
            g2dBuf.setFont(new Font("Arial",1,135));
            g2dBuf.drawString("Flying Bird",215,FRAM_HEIGHT/2 + 60);
            //title
            g2dBuf.setColor(Color.white);
            g2dBuf.setFont(new Font("Arial",1,140));
            g2dBuf.drawString("Flying Bird",200,FRAM_HEIGHT/2 + 50);

            //level display and control
            g2dBuf.setColor(Color.white);
            g2dBuf.setFont(new Font("Arial",1,40));
            g2dBuf.drawString("level "+ (gameBarrierLayer.getSpeed()-4),485,FRAM_HEIGHT/2+220);

            //start game tip
            g2dBuf.setColor(transparentWord);
            g2dBuf.setFont(new Font("Arial",1,40));
            g2dBuf.drawString("Press SPACE to start",330,FRAM_HEIGHT/2+270);

            g2dBuf.dispose();
            g.drawImage(buffimg, 0, 0, null);

        } else if(IsCease && bird.hp > 0 ){
            //暂停

            Graphics2D g2dBuf =  buffimg.createGraphics();

            if ( maskDown <= 2 ) {
                Color CeaseBlack = new Color(0, 0, 0, 40);
                g2dBuf.setColor(CeaseBlack);
                g2dBuf.fillRect(0, 0, FRAM_WIDTH, FRAM_HEIGHT);
                maskDown ++;
            }

            String GAMEPAUSE = "PAUSE";
            String TIP_PAUSE = "Press \"ESC\" to resume";

            g2dBuf.setColor(Color.white);
            g2dBuf.setFont(new Font("Arial",1,120));
            g2dBuf.drawString(GAMEPAUSE,300,FRAM_HEIGHT/2 + 50);

            Color test = new Color(255, 255, flash, flash);
            g2dBuf.setColor(test);
            g2dBuf.setFont(new Font("Arial",1,30));
            g2dBuf.drawString(TIP_PAUSE,330,FRAM_HEIGHT/2+270);

            if(IsResetHistory){
                g2dBuf.setFont(new Font("Arial",1,10));
                g2dBuf.drawString("Reset",FRAM_WIDTH/2 -10,FRAM_HEIGHT/2+285);
            }
            g.drawImage(buffimg, 0, 0, null);
        }
    }

    //press key
    public void add (KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                if (IsFirstStart) {
                    speed--;
                    gameBarrierLayer.setSpeed(speed);
                    System.out.println("level down");
                }
                break;
            case KeyEvent.VK_UP:  //按上飞
                if(IsFirstStart){
                    speed++;
                    gameBarrierLayer.setSpeed(speed);;
                    System.out.println("level up");
                } else{
                    bird.fly(1);
                }
                break;
            case KeyEvent.VK_SPACE: //空格
                if(bird.hp == 0){    // 死后重开
                    WantToBack = 0;
                    restart();
                } else if (bird.hp >0) { // 取消menu
                    IsFirstStart = false;
                }
                break;
            case KeyEvent.VK_NUMPAD0: // 按小键盘零重置历史记录
                if(bird.hp == 0){
                    scoreHistory.setScore(0);
                    IsResetHistory = true;
                }
            case KeyEvent.VK_W: // w上飞
                bird.fly(1);
                break;
            case KeyEvent.VK_ESCAPE: //Esc
                if(!IsCease && bird.hp >0 && !IsFirstStart ){ //正常暂停
                    pause();
                } else if (IsCease && bird.hp > 0 && !IsFirstStart){ //正常继续
                    resume();

                } else if(!IsCease && bird.hp == 0 && !IsFirstStart){ //返回菜单
                    BackToMenuOrNot();
                    System.out.println("esc");
                } else if(IsFirstStart){
                    BackToExit();

                }

        }
    }

    //release
    public void minu (KeyEvent e){ //常制
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: // 不按就向下
                bird.fly(5);
                break;
            case KeyEvent.VK_W: // 不按就向下
                bird.fly(5);
                break;
        }
    }

    public void restart(){  //重开
        WantToExit = 0;
        gameBarrierLayer.restart();
        bird.restart();
        gameTime.restart();
        maskDown = 0;
        IsNewHistory = false;
        IsResetHistory = false;
        Boom = 0;
        IsFirstStart = false;
    }

    public void pause(){
        gameTime.pause();
        IsCease = true;
        System.out.println("game pause");
    }

    public void resume(){
        gameTime.resume();
        maskDown = 0;
        IsCease = false;
        System.out.println("game resume");
    }

    public void BackToMenuOrNot() {  //是否回到menu
        if (WantToBack == 0) {
            WantToBack = 1;
        } else if (WantToBack == 1) {
            WantToBack = 2;
            bird.hp = 1;
            restart();
            IsFirstStart = true;
            WantToBack = 0;
        }

    }

    public void BackToExit() {  //是否回到menu
        if (WantToExit == 0) {
            WantToExit = 1;
        } else if (WantToExit == 1) {
            WantToExit = 2;
            System.exit(0);
            WantToExit = 0;

        }

    }
}
