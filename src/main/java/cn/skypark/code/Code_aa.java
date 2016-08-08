package cn.skypark.code;

import java.awt.image.BufferedImage;

public abstract class Code_aa {
    public Code_aa() {
    }
    public abstract String getCode(BufferedImage var1);
    public int pixelConvert(int pixel) {
        byte result = 0;
        int r = pixel >> 16 & 255;
        int g = pixel >> 8 & 255;
        int b = pixel & 255;
        int tmp = r * r + g * g + b * b;
        if(tmp > '쀀') {
            result = 1;
        }

        return result;
    }
}