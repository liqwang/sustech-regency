package com.sustech.regency.util;

import cn.hutool.core.codec.Base64Encoder;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Cleanup;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

import static javax.imageio.ImageIO.createImageOutputStream;
import static org.springframework.util.FileCopyUtils.copyToByteArray;

public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final int QRCODE_SIZE = 300; //二维码边长的像素
    private static final int LOGO_WIDTH = 60;
    private static final int LOGO_HEIGHT = 60;
    private static final Map<EncodeHintType, Object> HINTS =
            Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                    EncodeHintType.CHARACTER_SET, CHARSET,
                    EncodeHintType.MARGIN, 1);

    /**
     * <a href="https://www.youtube.com/watch?v=XW8sgT_D0To">二维码编码科普</a>
     * @param imgPath 嵌入二维码中央的Logo路径(resource中)，如果为null，则不添加
     * @param needCompress 如果嵌入图片，是否需要压缩
     * @return 支付二维码的Base64编码
     */
    @SneakyThrows
    public static String encode(String aliPayUrl, String imgPath, boolean needCompress){
        BitMatrix bitMatrix = new MultiFormatWriter().encode(aliPayUrl, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, HINTS);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        //TYPE_INT_RGB指定ARGB格式:0xAARRGGBB
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //将bitMatrix中的二进制数据渲染进BufferedImage中
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //true黑，false白
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath != null) {
            embedImage(image, imgPath, needCompress);
        }
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
        @Cleanup ImageOutputStream imageOut = createImageOutputStream(out);
        ImageIO.write(image,"JPEG",imageOut);
        byte[] bytes = copyToByteArray(new ByteArrayInputStream(out.toByteArray()));
        return Base64Encoder.encode(bytes);
    }

    public static String encode(String content){
        return encode(content,null,false);
    }

    public static String decode(File qrCode) throws Exception {
        BufferedImage image = ImageIO.read(qrCode);
        if (image == null) {
            return null;
        }
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Map<DecodeHintType, String> hint = Map.of(DecodeHintType.CHARACTER_SET, CHARSET);
        MultiFormatReader formatReader = new MultiFormatReader();
        return formatReader.decode(bitmap, hint).getText();
    }

    public static String decode(String path) throws Exception {
        return decode(new File(path));
    }

    /**
     * 将图片嵌入二维码中央
     * @param qrCode 需要嵌入中央Logo的二维码
     * @param imgPath 嵌入二维码中央的Logo路径(resource中)，如果为null，则不添加
     */
    @SneakyThrows(IOException.class)
    private static void embedImage(BufferedImage qrCode, String imgPath, boolean needCompress){
        @SuppressWarnings("ConstantConditions")
        Image srcImage = ImageIO.read(QRCodeUtil.class.getClassLoader().getResourceAsStream(imgPath));
        int width = srcImage.getWidth(null);
        int height = srcImage.getHeight(null);
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            Graphics graphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB).getGraphics();
            graphics.drawImage(image, 0, 0, null); // 绘制缩小后的图
            graphics.dispose();
            srcImage = image;
        }
        // 插入LOGO
        Graphics2D graph = qrCode.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(srcImage, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
}