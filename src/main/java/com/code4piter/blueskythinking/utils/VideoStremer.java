package com.code4piter.blueskythinking.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class VideoStremer {

    public static boolean saveImage(String url, String fileName) {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(url);
        try {
            g.start();
            BufferedImage bi = new Java2DFrameConverter().convert(g.grab());
            ImageIO.write(bi, "png", new File(fileName));
            g.stop();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
