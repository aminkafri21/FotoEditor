package function;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImageFunction {
    private Main main;

    private JSlider brightSlider, blurSlider;
    private JDialog brightDialog, blurDialog;
    private boolean brightSliderOn, blurSliderOn;
    public int brightVal, radius = 0;
    public boolean monoOn = false;


    public ImageFunction(Main main) {
        this.main = main;
    }
    public void adjustBrightness() {
        if (brightSliderOn) {
            brightSliderOn = false;
            brightDialog.setVisible(false);
            return;
        }
        if (main.imageCanvas.image == null) {
            return;
        }

        brightSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        brightSlider.setMajorTickSpacing(25);
        brightSlider.setPaintLabels(true);
        brightSlider.setPaintTicks(true);
        brightSlider.setSnapToTicks(false);
        brightSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            brightVal = source.getValue();
//            System.out.println(brightVal);
            main.imageCanvas.repaint();
        });

        brightDialog = new JDialog(main.window, "Adjust Brightness");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            brightSliderOn = false;
            brightDialog.setVisible(false);
        });

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // For OK button
        southPanel.add(okButton);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(brightSlider, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        brightDialog.setContentPane(contentPane);

        brightSliderOn = true;
        brightDialog.pack(); // Removed
        brightDialog.setMinimumSize(new Dimension(330, 150));
        brightDialog.setLocationRelativeTo(main.window);
        brightDialog.setVisible(true);
    }
    public void adjustBlur() {
        if (blurSliderOn) {
            blurSliderOn = false;
            blurDialog.setVisible(false);
            return;
        }
        if (main.imageCanvas.image == null) {
            return;
        }

        blurSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);
        blurSlider.setMajorTickSpacing(2);
        blurSlider.setPaintLabels(true);
        blurSlider.setPaintTicks(true);
        blurSlider.setSnapToTicks(true);
        blurSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            radius = source.getValue();
            main.imageCanvas.repaint();
        });

        blurDialog = new JDialog(main.window, "Adjust Blurring");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            blurSliderOn = false;
            blurDialog.setVisible(false);
        });
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // For OK button
        southPanel.add(okButton);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(blurSlider, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        blurDialog.setContentPane(contentPane);

        blurSliderOn = true;
        blurDialog.pack(); // Removed
        blurDialog.setMinimumSize(new Dimension(330, 150));
        blurDialog.setLocationRelativeTo(main.window);
        blurDialog.setVisible(true);
    }
    public BufferedImage rescale(BufferedImage image, int percentage) {
        BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int val = (int) ((percentage / 2.0f) * 255 / 100.0f);
        for (int y = 0; y < main.imageCanvas.image.getHeight(); y++) {
            for (int x = 0; x < main.imageCanvas.image.getWidth(); x++) {
                int pixel = main.imageCanvas.image.getRGB(x, y);

                int red = ((pixel >> 16) & 0xFF) + val;
                int green = ((pixel >> 8) & 0xFF) + val;
                int blue =  (pixel & 0xFF) + val;
                red = Math.min(255, Math.max(0, red + val));
                green = Math.min(255, Math.max(0, green + val));
                blue = Math.min(255, Math.max(0, blue + val));

                pixel = (255 << 24) | (red << 16) | (green << 8) | blue;

                img.setRGB(x, y, pixel);
            }
        }
        return img;
    }
    public BufferedImage monochrome(BufferedImage image) {
        BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                int avg = (int) ((pixel >> 16 & 0xFF) * 0.299 + (pixel >> 8 & 0xFF) * 0.587 + (pixel & 0xFF) * 0.114);
                outputImage.setRGB(x, y, (avg << 16) | (avg << 8) | avg);
            }
        }
        return outputImage;
    }
    public void setMono() {
       monoOn = !monoOn;
    }
    public BufferedImage boxBlur(int radius, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int area = (2 * radius + 1) * (2 * radius + 1);
        for (int x = radius; x < width - radius; x++) {
            for (int y = radius; y < height - radius; y++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                for (int i = x - radius; i <= x + radius; i++) {
                    for (int j = y - radius; j <= y + radius; j++) {
                        int pixel = image.getRGB(i, j);
                        sumRed += (pixel >> 16) & 0xFF;
                        sumGreen += (pixel >> 8) & 0xFF;
                        sumBlue += pixel & 0xFF;
                    }
                }
                int avgRed = sumRed / area;
                int avgGreen = sumGreen / area;
                int avgBlue = sumBlue / area;
                int newPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;
                outputImage.setRGB(x, y, newPixel);
            }
        }
        return outputImage;
    }

}

