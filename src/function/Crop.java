/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import main.Main;

/**
 *
 * @author wanab
 */
public class Crop {
    private Main main;
    private boolean isCropping = false;
    private Rectangle cropArea;
    private Point startPoint;

    public Crop(Main main) {
        this.main = main;
        this.cropArea = new Rectangle();
        initializeCropping();
    }

    private void initializeCropping() {
        main.imageCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isCropping) {
                    startPoint = e.getPoint();
                    cropArea.setBounds(startPoint.x, startPoint.y, 0, 0);
                    main.imageCanvas.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isCropping) {
                    isCropping = false;
                    applyCrop();
                }
            }
        });

        main.imageCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isCropping) {
                    int width = e.getX() - startPoint.x;
                    int height = e.getY() - startPoint.y;
                    cropArea.setSize(width, height);
                    main.imageCanvas.repaint();
                }
            }
        });
    }

    public void applyCrop() {
        if (cropArea != null && cropArea.width > 0 && cropArea.height > 0) {
            BufferedImage originalImage = main.imageCanvas.image;
            
            // Ensure that the crop area is within the bounds of the image
            cropArea.x = Math.max(0, cropArea.x);
            cropArea.y = Math.max(0, cropArea.y);
            cropArea.width = Math.min(cropArea.width, originalImage.getWidth() - cropArea.x);
            cropArea.height = Math.min(cropArea.height, originalImage.getHeight() - cropArea.y);

            // Create a cropped image
            BufferedImage croppedImage = originalImage.getSubimage(cropArea.x, cropArea.y, cropArea.width, cropArea.height);

            // Update the ImageCanvas with the cropped image
            main.imageCanvas.setEditedImage(croppedImage);
            main.imageCanvas.repaint();
        }
    }
    
    public void startCropping() {
        isCropping = true;
        cropArea = new Rectangle();
        main.imageCanvas.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void stopCropping() {
        isCropping = false;
        main.imageCanvas.setCursor(Cursor.getDefaultCursor());
    }

    public void paintCroppingOverlay(Graphics g) {
        if (isCropping) {
            g.setColor(new Color(128, 128, 128, 128));
            g.fillRect(cropArea.x, cropArea.y, cropArea.width, cropArea.height);
            g.setColor(Color.BLACK);
            g.drawRect(cropArea.x, cropArea.y, cropArea.width, cropArea.height);
        }
    }
    
}
