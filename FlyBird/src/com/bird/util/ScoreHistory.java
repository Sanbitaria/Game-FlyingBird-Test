package com.bird.util;

import java.io.*;

public class ScoreHistory {
    private long score;

    File file = new File("D:\\ScoreHistory.txt");


    public ScoreHistory(){}

    public long getScore(){
        long s;
        if (!file.exists()){
            try {
                file.createNewFile();
                System.out.println("create right");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("create false");
            }
        }

        BufferedReader readIn = null;
        try {
            readIn = new BufferedReader(new FileReader(file));
            String line = null;
            line = readIn.readLine();
            if(line == null){
                setScore(0);
            }
            s = Long.parseLong(line);
            readIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    public void setScore(long score){
        String line = String.valueOf(score);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
