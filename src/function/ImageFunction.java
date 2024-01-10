package function;

import main.Main;

import javax.swing.*;
import java.awt.*;

public class ImageFunction {
    private Main main;
    JInternalFrame brightnessFrame, blurFrame;
    JSlider brightnessSlider, blurSlider;
    public ImageFunction(Main main) {
        this.main = main;
    }
    public void adjustBrightness() {
        brightnessSlider = new JSlider(JSlider.HORIZONTAL, -255,  255, 0);
        brightnessSlider.setMajorTickSpacing(85);
        brightnessSlider.setMinorTickSpacing(10);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);

        brightnessFrame = new JInternalFrame("Adjust Brightness", false, true, false, false);
        brightnessFrame.setSize(new Dimension(320, 200));
        brightnessFrame.setLocation(main.desktopPane.getAllFrames().length*10, main.desktopPane.getAllFrames().length*10);
        brightnessFrame.add(brightnessSlider);
        brightnessFrame.setVisible(true);
        main.window.add(brightnessFrame);
    }
    public void blur() {
        blurSlider = new JSlider(JSlider.HORIZONTAL, 100,  0);
        blurSlider.setMajorTickSpacing(10);
        blurSlider.setMinorTickSpacing(5);
        blurSlider.setPaintTicks(true);
        blurSlider.setPaintLabels(true);

        blurFrame = new JInternalFrame("Adjust Blur", false, true, false, false);
        blurFrame.setSize(new Dimension(320, 200));
        blurFrame.setLocation(main.desktopPane.getAllFrames().length*10, main.desktopPane.getAllFrames().length*10);
        blurFrame.add(blurSlider);
        blurFrame.setVisible(true);
        main.window.add(blurFrame);
    }
}
