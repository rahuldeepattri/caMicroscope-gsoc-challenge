package com.rahuldeepattri.poc.service;

import com.rahuldeepattri.poc.model.SplitRGBResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BufferedImageRGBSplitter implements RGBImageSplitter {

    @Value("${domain}")
    private String domain;

    @Override
    public SplitRGBResponse split(MultipartFile image) throws IOException {
//        File testFile = new File("test");
//        FileUtils.writeByteArrayToFile(testFile, image.getBytes());

        BufferedImage receivedImage = ImageIO.read(image.getInputStream());
        List<BufferedImage> splitRGBList = BufferedImageConverter.splitRGB(receivedImage);
        SplitRGBResponse response = new SplitRGBResponse();
        response.setRed(saveBufferedImage(splitRGBList.get(0)));
        response.setBlue(saveBufferedImage(splitRGBList.get(1)));
        response.setGreen(saveBufferedImage(splitRGBList.get(2)));
        return response;
    }

    private URL saveBufferedImage(BufferedImage img) {
        File file = null;
        URL url = null;
        try {
            file = new File("./data/" + UUID.randomUUID().toString() + ".jpg");
            ImageIO.write(img, "jpg", file);
            url = new URL("http://" + domain + "/data/" + file.getName());
        } catch (IOException e) {
            log.error("Error saving file", e);
            return null;
        }

        return url;
    }

}


class BufferedImageConverter {
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static List<BufferedImage> splitRGB(BufferedImage src) throws IOException {
        BufferedImage red = deepCopy(src);
        BufferedImage green = deepCopy(src);
        BufferedImage blue = deepCopy(src);
        // get width and height
        int width = src.getWidth();
        int height = src.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setRed(y, x, red);
                setGreen(y, x, green);
                setBlue(y, x, blue);
            }
        }
        return Arrays.asList(red, green, blue);
    }


    private static void setRed(int y, int x, BufferedImage img) {
        int p = img.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;

        // set new RGB
        // keeping the r value same as in original
        // image and setting g and b as 0.
        p = (a << 24) | (r << 16) | (0 << 8) | 0;

        img.setRGB(x, y, p);
    }

    private static void setGreen(int y, int x, BufferedImage img) {
        int p = img.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int g = (p >> 8) & 0xff;

        // set new RGB
        // keeping the g value same as in original
        // image and setting r and b as 0.
        p = (a << 24) | (0 << 16) | (g << 8) | 0;

        img.setRGB(x, y, p);
    }

    private static void setBlue(int y, int x, BufferedImage img) {
        int p = img.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int b = p & 0xff;

        // set new RGB
        // keeping the b value same as in original
        // image and setting r and g as 0.
        p = (a << 24) | (0 << 16) | (0 << 8) | b;

        img.setRGB(x, y, p);
    }

}

