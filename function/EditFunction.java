/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.Main;

/**
 *
 * @author wanab
 */
public class EditFunction {
    Main main;

    public EditFunction (Main main){
    this.main = main;
    }

    public void crop() {
        BufferedImage originalImage = main.imageCanvas.image;

        if (main.imageCanvas.image != null){
        if (originalImage != null) {
            // Prompt the user for crop coordinates
            int x = Integer.parseInt(JOptionPane.showInputDialog("Enter X coordinate:"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("Enter Y coordinate:"));
            int width = Integer.parseInt(JOptionPane.showInputDialog("Enter width:"));
            int height = Integer.parseInt(JOptionPane.showInputDialog("Enter height:"));

            x = Math.max(0, x);
            y = Math.max(0, y);
            width = Math.min(width, originalImage.getWidth() - x);
            height = Math.min(height, originalImage.getHeight() - y);

            BufferedImage croppedImage = new BufferedImage(width, height, originalImage.getType());

            for (int i = 0; i < width && x + i < originalImage.getWidth(); i++) {
            for (int j = 0; j < height && y + j < originalImage.getHeight(); j++) {
                int rgb = originalImage.getRGB(x + i, y + j);
                croppedImage.setRGB(i, j, rgb);
                    }
                }
                main.imageCanvas.image = croppedImage;
                main.imageCanvas.repaint();
            }
        }
    }
    
    public BufferedImage resize() {
        BufferedImage originalImage = main.imageCanvas.image;

        if (originalImage != null) {
            JTextField widthField = new JTextField(5);
            JTextField heightField = new JTextField(5);

            JCheckBox maintainAspectRatioCheckbox = new JCheckBox("Maintain Aspect Ratio");

            JPanel inputPanel = new JPanel();
            inputPanel.add(new JLabel("Width:"));
            inputPanel.add(widthField);
            inputPanel.add(new JLabel("Height:"));
            inputPanel.add(heightField);
            inputPanel.add(maintainAspectRatioCheckbox);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    inputPanel,
                    "Resize Image",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                // Parse user input for new dimensions
                int newWidth = Integer.parseInt(widthField.getText());
                int newHeight = Integer.parseInt(heightField.getText());
                boolean maintainAspectRatio = maintainAspectRatioCheckbox.isSelected();

                // Calculate the new dimensions while maintaining aspect ratio
                if (maintainAspectRatio) {
                    double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                    if (newWidth > newHeight) {
                        newHeight = (int) (newWidth / aspectRatio);
                    } else {
                        newWidth = (int) (newHeight * aspectRatio);
                    }
                }
                Image tmp = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                resizedImage.createGraphics().drawImage(tmp, 0, 0, null);

                return resizedImage;
            }
        }
        return null;
    }
    
    public Rectangle select() {
        BufferedImage originalImage = main.imageCanvas.image;

        if (originalImage != null) {
            Rectangle selectedRegion = new Rectangle();

            // Add a mouse adapter to the ImageCanvas to handle selection
            main.imageCanvas.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Set the starting point of the selection
                    selectedRegion.setLocation(e.getPoint());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    selectedRegion.setSize(
                            e.getPoint().x - (int) selectedRegion.getX(),
                            e.getPoint().y - (int) selectedRegion.getY()
                    );

                    selectedRegion.setSize(0, 0);
                    main.imageCanvas.repaint();
                }
            });

            // Add a mouse motion listener to update the selection rectangle
            main.imageCanvas.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // Update the size of the selection as the mouse is dragged
                    selectedRegion.setSize(
                            e.getPoint().x - (int) selectedRegion.getX(),
                            e.getPoint().y - (int) selectedRegion.getY()
                    );
                    main.imageCanvas.repaint();
                }
            });

            return selectedRegion;
        }
        return null;
    }
}
