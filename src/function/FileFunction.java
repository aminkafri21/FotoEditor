/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Main;

/**
 *
 * @author user
 */
public class FileFunction {
    Main main;
    public String imagePath;
    public String imageName;
    JFileChooser fileChooser = new JFileChooser();
    public String savedImagePath;
    File savedImageFile;
    public boolean fileIsOpen = false;
    public boolean isProjectSaved = false;
    public FileFunction(Main main) {
        this.main = main;
    }
    
    public void New() {
        /*if(savedImagePath == null && fileIsOpen == true) {
            saveAs();
        }*/
        if (savedImagePath != null && fileIsOpen != true)
        if (!isProjectSaved) {
                } else {
                int choice = JOptionPane.showConfirmDialog(main.window,
                    "Do you want to save the current project before opening a new image?",
                    "Save Project",
                    JOptionPane.YES_NO_CANCEL_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    save();
                    if (isProjectSaved) {
                        open();
                    }
                } else if (choice == JOptionPane.NO_OPTION) {
                    open();
                }
            }
        savedImagePath = null;
        fileIsOpen = false;
        imagePath = null;
        main.imageCanvas.clearImage();
        main.imageCanvas.clearEditedImage();
        main.window.setTitle("Untitled");
    }
    
    public void save() {
        if(savedImagePath == null) {
            if (main.imageFunc.isImageModified()) {
            //if (main.imageCanvas.editedImage != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Project");
            int userSelection = fileChooser.showSaveDialog(main.window);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(main.imageCanvas.editedImage, "png", fileToSave);
                    JOptionPane.showMessageDialog(main.window, "Project saved successfully!");
                    main.imageFunc.resetImageModifiedFlag();
                    isProjectSaved = true;
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(main.window, "Error saving project!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else{
            try{
                ImageIO.write(main.imageCanvas.image, imageName.split("\\.")[1], savedImageFile);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public void resetSave() {
        isProjectSaved = false;
    }
    
    public void Exit() {
        System.exit(0);
    }
    
    public void saveAs() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Image", "jpg", "gif", "png"
        );
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(main.window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            savedImageFile = fileChooser.getSelectedFile();
            savedImagePath = savedImageFile.getAbsolutePath();
            try{
                ImageIO.write(main.imageCanvas.image, imageName.split("\\.")[1], savedImageFile);
            }catch(IOException e){
                e.printStackTrace();
            }
           
        }
    }
    
    public void open() {
        if (fileIsOpen) {
        if (!isProjectSaved) {
            int choice = JOptionPane.showConfirmDialog(main.window,
                    "Do you want to save the current project before opening a new image?",
                    "Save Project",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                save();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
                }
            }
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Image", "jpg", "gif"
        );
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(main.window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            imageName = fileChooser.getSelectedFile().getName();
            main.window.setTitle(imageName.split("\\.")[0]);
            main.imageCanvas.repaint();
            fileIsOpen = true;
            isProjectSaved = true;
            }
        main.imageFunc.resetImageModifiedFlag();
        main.imageCanvas.clearImage();
        main.imageCanvas.clearEditedImage();
    }
}
