package com.bird.util;

public class GameTime {

    private static final GameTime Instance = new GameTime();
    //start
    private long beginTime;
    //now
    private long nowTime;
    //end
    private long endTime = 0;
    //time difference
    private long differ;

    //time between pause and resume
    private long pauseTime = 0 ;
    private long resumeTime = 0 ;

    private boolean IsPause = false;

    private GameTime(){};

    public static GameTime getInstance(){
        return Instance;
    }

    public  void begin(){
        beginTime = System.currentTimeMillis();
        endTime = 0;
    }

    public void  end(){
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }
    }

    public long differ(){
        if(!IsPause) {

            long Cross = resumeTime - pauseTime;
            beginTime += Cross;
            resumeTime = 0;
            pauseTime = 0;

            if (endTime == 0) {
                nowTime = System.currentTimeMillis();
                differ = (nowTime - beginTime) / 1000;
            } else {
                differ = (endTime - beginTime) / 1000;
            }
        }

        return differ;

    }

    public void restart() {
        begin();
    }

    public void pause(){
        pauseTime = System.currentTimeMillis();
        IsPause = true;
    }
    public void resume(){
        resumeTime = System.currentTimeMillis();
        IsPause = false;
    }

    public long getNowTime(){
        return System.currentTimeMillis();
    }

}

