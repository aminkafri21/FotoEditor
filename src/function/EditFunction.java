/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import main.Main;

/**
 *
 * @author wanab
 */
public class EditFunction {
    Main main;
    Crop cropTool;
    private List<BufferedImage> imageHistory;
    private int currentIndex;
    private Stack<BufferedImage> editHistory;
    private Stack<BufferedImage> redoHistory;

    public EditFunction (Main main){
    this.main = main;
    this.cropTool = new Crop(main);
    this.editHistory = new Stack<>();
    this.redoHistory = new Stack<>();
    this.imageHistory = new ArrayList<>();
    this.currentIndex = -1;
    }
    
    public void undo(){
        if (currentIndex > 0) {
            currentIndex--;
            BufferedImage previousState = imageHistory.get(currentIndex);
            main.imageCanvas.loadImage(previousState);
            main.imageCanvas.repaint();
        }
    }
    
    public void saveState(){
        BufferedImage currentState = main.imageCanvas.image;
        BufferedImage clonedState = new BufferedImage(
                currentState.getWidth(),
                currentState.getHeight(),
                currentState.getType()
        );

        clonedState.getGraphics().drawImage(currentState, 0, 0, null);

        if (currentIndex < imageHistory.size() - 1) {
            imageHistory.subList(currentIndex + 1, imageHistory.size()).clear();
        }

        imageHistory.add(clonedState);
        currentIndex++;
    }

    
    public void redo (){
    if (!redoHistory.isEmpty()) {
        BufferedImage nextState = redoHistory.pop();
        editHistory.push(cloneImage(main.imageCanvas.image));
        main.imageCanvas.loadImage(nextState);
        }
    }
    
    private BufferedImage cloneImage(BufferedImage image) {
        if (image == null) {
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage clonedImage = new BufferedImage(width, height, image.getType());
        clonedImage.getGraphics().drawImage(image, 0, 0, null);

        return clonedImage;
    }

    public void crop() {
        cropTool.applyCrop();
    }
    
    public void resize(){
        
    }
    
    /*public void select(){
    }*/
    
}
