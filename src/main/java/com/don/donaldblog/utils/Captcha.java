package com.don.donaldblog.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {
    private static String STRCODE = "23456789ABCDEFGHJKLMNPQRSTUVXYZ";
    Random ran = new Random();
    private String code;


    public BufferedImage  getImageCode(int width, int height)
    {
        if (width < 60) {
            width = 60;
        }
        if (height < 30) {
            height = 30;
        }

        BufferedImage image = createImage(width, height);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        // 字体
        Font font = new Font("宋体", Font.PLAIN, 18);
        int codeLength = STRCODE.length();
        StringBuilder stringBuilder = new StringBuilder(4);
        for(int i = 0; i< 4; i++) {
            String c = STRCODE.charAt(ran.nextInt(codeLength - 1))+ "";
            stringBuilder.append(c);
            g2.setFont(font);
            float x = i * 1.0F * width / 4;
            g2.drawString(c, x, height - 5);
        }
        g2.dispose();
        this.code = stringBuilder.toString();
        drowLine(image, width, height);
        return image;
    }

    public String getCode()
    {
        return this.code;
    }

    private BufferedImage createImage(int width, int height)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        // 背景
        g2.setColor(getRandomColor());
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        return image;
    }

    private void drowLine(BufferedImage image, int width, int height)
    {
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        for(int i = 0; i < 3; i++) {
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(getRandomColor());
            g2.drawLine(x1, y1, x2, y2);
        }
        g2.dispose();
    }

    private Color getRandomColor()
    {
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        return new Color(r, g, b);
    }
}
