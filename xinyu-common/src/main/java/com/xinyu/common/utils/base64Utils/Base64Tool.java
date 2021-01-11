
package com.xinyu.common.utils.base64Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("restriction")
public class Base64Tool {

    /**
     * 将Base64字符串转成MultipartFile
     * @param base64 Base64字符串
     * @return MultipartFile
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            //BASE64Decoder decoder = new BASE64Decoder();
            Decoder decoder = Base64.getDecoder();
            byte[] b = new byte[0];
            b = decoder.decode(baseStrs[1]);

            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一张网络图片转化成Base64字符串
     * @param imgURL 网络资源位置
     * @return Base64字符串(png格式，如需其他格式请自行修改return格式)
     */
    public static String GetImageStrFromUrl(String imgURL) {
        byte[] data = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            data = new byte[inStream.available()];
            inStream.read(data);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Encoder encoder = Base64.getEncoder();
        // 返回Base64编码过的字节数组字符串
        return "data:image/png;base64,"+encoder.encodeToString(data);
    }

    /**
     * MultipartFile转换成Base64
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    public static String multipartToBase64(MultipartFile multfile){
        byte[] data = null;
        InputStream inStream;
        try {
            inStream = multfile.getInputStream();
            data = new byte[inStream.available()];
            inStream.read(data);
            inStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Encoder encoder = Base64.getEncoder();
        return "data:image/png;base64,"+encoder.encodeToString(data);
    }
}
