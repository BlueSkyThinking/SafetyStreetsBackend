package com.code4piter.blueskythinking.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class VideoStremer {

    public static byte[] saveImage(String url) {
        byte[] imageBytes = null;
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(url);
        try {
            g.start();
            BufferedImage bi = new Java2DFrameConverter().convert(g.grab());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", output);
            imageBytes = output.toByteArray();
            output.close();
            g.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBytes;
    }
}
