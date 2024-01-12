
package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageCanvas extends JPanel{
    Main main;
    public BufferedImage image = null;
    public ImageCanvas(Main main) {
        this.main = main;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    
    public void loadImage(File path) {
        try {
            image = ImageIO.read(path);
        } catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (main.fileFunc.imagePath != null) {
            loadImage(new File(main.fileFunc.imagePath));
            int win_W = main.window.getWidth();
            int win_H = main.window.getHeight();
            int img_W = image.getWidth();
            int img_H = image.getHeight();
//            System.out.println(image.getRaster().getDataBuffer().getDataType());

            // Calculate the scale to fit the image within the window while maintaining aspect ratio
            double scale = Math.min((double) win_W / img_W, (double) win_H / img_H);

            // Calculate the new width and height
            int new_W = (int) (img_W * scale);
            int new_H = (int) (img_H * scale);

            // Calculate the position to center the image
            int winCenterx = (win_W - new_W) / 2;
            int winCentery = (win_H - new_H) / 2;

            // Draw the image
            image = main.imageFunc.rescale(image, (int) main.imageFunc.brightVal);
//            image = main.imageFunc.blurImage(image, main.imageFunc.blurVal);
            g.drawImage(image, winCenterx, winCentery, new_W, new_H, null);
        }
    }
}
