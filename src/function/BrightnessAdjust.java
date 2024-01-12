/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.awt.image.BufferedImage;

/**
 *
 * @author wanab
 */
public class BrightnessAdjust {
    
    public static BufferedImage adjustBrightness(BufferedImage image, float factor) {
        int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage editedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int rgb = image.getRGB(x, y);

            int alpha = (rgb >> 24) & 0xFF;
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            // Apply brightness adjustment
            red = (int) (red + 255 * factor);
            green = (int) (green + 255 * factor);
            blue = (int) (blue + 255 * factor);

            // Ensure color values are within the valid range
            red = Math.min(255, Math.max(0, red));
            green = Math.min(255, Math.max(0, green));
            blue = Math.min(255, Math.max(0, blue));

            int adjustedRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
            editedImage.setRGB(x, y, adjustedRGB);
        }
    }
    return editedImage;
    }
}