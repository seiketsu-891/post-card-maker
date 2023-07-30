package com.louie.coding.util;

import io.netty.util.internal.StringUtil;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FileCompressUtil {
    static final String MSG_COMPRESS_IMG_FAILED = "图片压缩失败！";

    public static String compressBase64Img(String base64Img, int width) {
        if (StringUtil.isNullOrEmpty(base64Img) || width <= 0) {
            throw new RuntimeException(MSG_COMPRESS_IMG_FAILED);
        }
        base64Img = base64Img.split(",")[1];
        String compressedBase64Image;
        try {
            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Img);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage originalImage = ImageIO.read(bis);

            BufferedImage resizedImage = Thumbnails.of(originalImage).width(width).asBufferedImage();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", bos);
            bos.close();
            byte[] resizedImageBytes = bos.toByteArray();
            compressedBase64Image = java.util.Base64.getEncoder().encodeToString(resizedImageBytes);
        } catch (Exception e) {
            throw new RuntimeException(MSG_COMPRESS_IMG_FAILED);
        }
        return compressedBase64Image;
    }
}
