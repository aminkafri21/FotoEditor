/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.Main;

/**
 *
 * @author wanab
 */
public class ImageFunction {
    Main main;
    public String imagePath;
    public BufferedImage editedImage;
    private JSlider brightnessSlider;
    boolean isImageModified;
    
    public ImageFunction(Main main) {
        this.main = main;
        //addMouseListeners();
        createBrightnessSlider();
    }
    
    public void createBrightnessSlider() {
        brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        brightnessSlider.setMajorTickSpacing(25);
        brightnessSlider.setMinorTickSpacing(5);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);

        brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int brightnessValue = brightnessSlider.getValue();
                float factor = (float) brightnessValue / 100f;
                adjustBrightness(factor);
            }
        });
    }

    public JSlider getBrightnessSlider() {
        return brightnessSlider;
    }

        public void showBrightnessDialog() {
        JOptionPane.showMessageDialog(
                main.window,
                brightnessSlider,
                "Brightness Adjustment",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    
    public void adjustBrightness(float factor) {
        if (main.fileFunc.imagePath != null) {
            BufferedImage originalImage = main.imageCanvas.image;
            editedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            editedImage = BrightnessAdjust.adjustBrightness(originalImage, factor);
            main.imageCanvas.setEditedImage(editedImage);
            main.imageCanvas.repaint();
            if (!areImagesEqual(originalImage, editedImage)) {
            main.imageCanvas.setEditedImage(editedImage);
            main.imageCanvas.repaint();
            isImageModified = true;
            main.imageCanvas.updateImage();
            }
        }
    }
    
    public void applyBlur() {
        BufferedImage originalImage = main.imageCanvas.image;
        if(originalImage != null){
            editedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            int [] [] kernel = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1},};
            int kernelSum = 9;
            
            for (int x = 1; x < editedImage.getWidth() - 1; x++){
                for (int y = 1; y < editedImage.getHeight() - 1; y++){
                    int r = 0, g = 0, b = 0;
                    for(int i = -1; i <= 1; i++){
                        for(int j = -1; j <= 1; j++){
                            int rgb = originalImage.getRGB(x + i, y + j);
                            r += ((rgb >> 16) & 0xFF) * kernel[i + 1] [j + 1];
                            g += ((rgb >> 8) & 0xFF) * kernel[i + 1] [j + 1];
                            b += (rgb & 0xFF) * kernel[i + 1] [j + 1];
                        }
                    }
                    r /= kernelSum;
                    g /= kernelSum;
                    b /= kernelSum;
                    
                    int newRGB = (r << 16) | (g << 8) | b;
                    editedImage.setRGB(x, y, newRGB);
                }
            }
            main.imageCanvas.setEditedImage(editedImage);
            main.imageCanvas.repaint();
            main.imageCanvas.updateImage();
            isImageModified = true;
            main.fileFunc.isProjectSaved = false;
        }
    }
    
    public void setEditedImage(BufferedImage editedImage) {
        this.editedImage = editedImage;
    }
    
    public static boolean areImagesEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            // Images have different dimensions
            return false;
        }

        int width = image1.getWidth();
        int height = image1.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    // Pixels at the same position are different
                    return false;
                }
            }
        }

        // All pixels are the same
        return true;
    }
    
    public boolean isImageModified() {
        return isImageModified;
    }
    
    public void resetImageModifiedFlag() {
        isImageModified = false;
    }
}