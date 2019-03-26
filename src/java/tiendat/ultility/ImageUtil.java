/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendat.ultility;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import tiendat.common.CrawlCommon;

/**
 *
 * @author DATTTSE62330
 */
public class ImageUtil {

    public static byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(imageBuff, "png", buffer);
            return buffer.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public static void saveImageFromURL(String imgURL, String filePath) throws IOException {
        FileOutputStream fos = null;
        ByteArrayOutputStream out = null;
        InputStream in = null;
        try {
            System.out.println("GETTING IMAGES: ");
            imgURL = imgURL.replace("%20", " ");
            URL url = new URL(imgURL);            
            String protocol = url.getProtocol();
            String host = url.getHost();
            String path = url.getPath();                        
            String encodedPath = URLEncoder.encode(path,"UTF-8").replace("+","%20").replace("%2F","/");            
            System.out.println("ENCODED: " + encodedPath);
            url = new URL(protocol, host, encodedPath);
            BufferedImage bufferImage = ImageIO.read(url);
            ImageIO.write(bufferImage, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }

    }
}
