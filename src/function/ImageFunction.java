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
    public int brightVal, blurVal;


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
            blurSlider.setVisible(false);
            return;
        }
        if (main.imageCanvas.image == null) {
            return;
        }

        blurSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        blurSlider.setMajorTickSpacing(25);
        blurSlider.setPaintLabels(true);
        blurSlider.setPaintTicks(true);
        blurSlider.setSnapToTicks(false);
        blurSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            blurVal = source.getValue();
            main.imageCanvas.repaint();
        });

        blurDialog = new JDialog(main.window, "Adjust Blurring");
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            blurSliderOn = false;
            blurSlider.setVisible(false);
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

    public BufferedImage blurImage(BufferedImage image, int percentage) {return null;}
    public void monochrome() {

    }
    public BufferedImage rescale(BufferedImage image, int percentage) {
        BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int val = (int) (percentage * 255 / 100);
        for (int y = 0; y < main.imageCanvas.image.getHeight(); y++) {
            for (int x = 0; x < main.imageCanvas.image.getWidth(); x++) {
                int pixel = main.imageCanvas.image.getRGB(x, y);

                // Extract color components (assuming ARGB format):
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

}

