package com.louie.coding.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class Base64Util {
    private static final int SIZE_THRESHOLD_1_MB = 1024 * 1024;

    /**
     * 将png图片的base64字符转转化为MultipartFile
     */
    public static MultipartFile base64PNGToMultipartFile(String base64Str) throws IOException {
        // 去掉data:image/png;base64,部分
        base64Str = base64Str.split(",")[1];
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        DiskFileItem fileItem = new DiskFileItem("file", "image/png", false, "temp.png", SIZE_THRESHOLD_1_MB, null);
        OutputStream os = fileItem.getOutputStream();
        os.write(bytes);
        os.flush();
        CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(fileItem);
        // 删除临时文件
        fileItem.delete();
        return commonsMultipartFile;
    }
}
