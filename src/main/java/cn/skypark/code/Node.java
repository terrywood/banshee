package cn.skypark.code;


import java.awt.image.BufferedImage;
import java.util.zip.CRC32;

public class Node {
    public int x1 = 0;
    public int x2 = 0;
    public int y1 = 0;
    public int y2 = 0;

    Node() {
    }

    public String getValue(Code_huatai code, BufferedImage image, PairXY xy) {
        String sInfo = "";
        String sData = "";

        for(int crc32 = this.y1; crc32 <= this.y2; ++crc32) {
            for(int id = this.x1; id <= this.x2; ++id) {
                int expRGB = code.pixelConvert(image.getRGB(id, crc32));
                if(expRGB == 1) {
                    sInfo = sInfo + " ";
                } else {
                    sInfo = sInfo + expRGB;
                    sData = sData + "[" + (id - this.x1) + "," + (crc32 - this.y1) + "]";
                }
            }

            sInfo = sInfo + "\n";
        }

        xy.x = this.x2 - this.x1 + 1;
        xy.y = this.y2 - this.y1 + 1;
        CRC32 var9 = new CRC32();
        var9.update(sData.getBytes());
        long var10 = var9.getValue();
        return sData;
    }
}