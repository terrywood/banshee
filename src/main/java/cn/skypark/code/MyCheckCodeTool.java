package cn.skypark.code;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by riky on 2016/8/8.
 */
public class MyCheckCodeTool {
    private Code_aa m_Code = null;

    public MyCheckCodeTool(String sType) {
        if(sType.equals("huatai")) {
           // this.m_Code = new Code_huatai();
        } else if(sType.equals("guojin")) {
            this.m_Code = new Code_guojin();
        }

    }

    public String getCheckCode_from_image(BufferedImage image) {
        return this.m_Code.getCode(image);
    }

    public String getCheckCode_from_file(String filename) {
        BufferedImage image = this.getImageFromFile(filename);
        return this.getCheckCode_from_image(image);
    }

    private BufferedImage getImageFromFile(String filename) {
        File file = new File(filename);
        BufferedImage output = null;

        try {
            output = ImageIO.read(file);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return output;
    }
}
