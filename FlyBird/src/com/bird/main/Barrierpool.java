package com.bird.main;

import java.util.ArrayList;
import java.util.List;

public class Barrierpool {

    private static List<Barrier>  pool = new ArrayList<>();

    public static final int  initCount = 16;

    public static final int maxCount = 20;

    static{
        for (int i=0; i  <initCount ; i++){
            pool.add(new Barrier());
        }
    }

    //get object from pool
    public static Barrier getPool(){
        int size = pool.size();
        if (size > 0){
            System.out.println("take one");
            return pool.remove(size - 1);
        } else {
            System.out.println("new object");
            return new Barrier();

        }
    }

    //return object to pool
    public static Barrier setPool(Barrier barrier){
        int size = pool.size();
        if (size < maxCount ){
            pool.add(barrier);
            System.out.println("return already");
        }
        return barrier;
    }


}
