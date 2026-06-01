package com.bird.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameUtil {
    //Load the images
    public static BufferedImage loadBufferedImage(String imgPath){
        // 从 resources 根目录读取
        InputStream is = GameUtil.class.getClassLoader().getResourceAsStream(imgPath);
        if (is == null) {
            System.err.println("ERROR:" + imgPath);
            return null;
        }
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
