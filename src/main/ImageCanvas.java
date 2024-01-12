
package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class ImageCanvas extends JPanel{
    Main main;
    public BufferedImage image;
    public BufferedImage editedImage;
    JLabel imageLabel = new JLabel();
    
    public ImageCanvas(Main main) {
        this.main = main;
    }
    
    public void loadImage(Object source) {
            if (!main.fileFunc.isProjectSaved) {
            int response = JOptionPane.showConfirmDialog(main.window,
                    "Do you want to save the current project before opening a new image?",
                    "Save Project",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                // Save the project
                main.fileFunc.save();
            } else if (response == JOptionPane.CANCEL_OPTION) {
                // Cancel the operation
                return;
                }
            }
        if (source instanceof File) {
            File file = (File) source;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (source instanceof BufferedImage) {
            image = (BufferedImage) source;
        }

        repaint();
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

            // Calculate the scale to fit the image within the window while maintaining aspect ratio
            double scale = Math.min((double) win_W / img_W, (double) win_H / img_H);

            // Calculate the new width and height
            int new_W = (int) (img_W * scale);
            int new_H = (int) (img_H * scale);

            // Calculate the position to center the image
            int winCenterx = (win_W - new_W) / 2;
            int winCentery = (win_H - new_H) / 2;

            // Draw the image
            g.drawImage(image, winCenterx, winCentery, new_W, new_H, null);
            }
        if (editedImage != null) {
        int win_W = main.window.getWidth();
        int win_H = main.window.getHeight();
        int img_W = editedImage.getWidth();
        int img_H = editedImage.getHeight();

        // Calculate the scale to fit the image within the window while maintaining aspect ratio
        double scale = Math.min((double) win_W / img_W, (double) win_H / img_H);

        // Calculate the new width and height
        int new_W = (int) (img_W * scale);
        int new_H = (int) (img_H * scale);

        // Calculate the position to center the image
        int winCenterx = (win_W - new_W) / 2;
        int winCentery = (win_H - new_H) / 2;

        // Draw the image
        g.drawImage(editedImage, winCenterx, winCentery, new_W, new_H, null);
        }
    }
    
    public void updateImage(){
        if (editedImage != null){
            repaint();
            ImageIcon imageIcon = new ImageIcon(editedImage);
            imageLabel.setIcon(imageIcon);
            imageLabel.revalidate();
        }
    }
    
    public Image getImage(){
        return image;
    }
    
    public void clearImage() {
        image = null;
    }
    
    public void clearEditedImage() {
        editedImage = null;
        repaint();
    }
    
    public void setEditedImage(BufferedImage editedImage) {
        this.editedImage = editedImage;
        repaint();
    }
    
}
