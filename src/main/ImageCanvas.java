package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageCanvas extends JPanel{
    Main main;
    public BufferedImage image = null;
    public BufferedImage originalImage = null;
    public String currentImagePath = null;

    public ImageCanvas(Main main) {
        this.main = main;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void loadImage(File path) {
        try {
            image = ImageIO.read(path);
            currentImagePath = path.getAbsolutePath();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (main.fileFunc.imagePath != null && !main.fileFunc.imagePath.equals(currentImagePath)) {
            loadImage(new File(main.fileFunc.imagePath));
        }
        if (image != null) {
            int win_W = main.window.getWidth();
            int win_H = main.window.getHeight();
            int img_W = image.getWidth();
            int img_H = image.getHeight();

            double scale = Math.min((double) win_W / img_W, (double) win_H / img_H);

            int new_W = (int) (img_W * scale);
            int new_H = (int) (img_H * scale);

            int winCenterX = (win_W - new_W) / 2;
            int winCenterY = (win_H - new_H) / 2;

            originalImage = deepCopy(image);

            originalImage = main.imageFunc.rescale(originalImage, (int) main.imageFunc.brightVal);
            originalImage = main.imageFunc.boxBlur(main.imageFunc.radius, originalImage);
            if (main.imageFunc.monoOn == true) {
                originalImage = main.imageFunc.monochrome(originalImage);
            }

            g.drawImage(originalImage, winCenterX, winCenterY, new_W, new_H, null);
        }
    }

    public BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
