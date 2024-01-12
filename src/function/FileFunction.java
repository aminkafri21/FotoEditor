/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
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
    boolean fileIsOpen = false;
    public FileFunction(Main main) {
        this.main = main;
    }
    
    public void New() {
        if(savedImagePath == null && fileIsOpen == true) {
            saveAs();
        }
        savedImagePath = null;
        fileIsOpen = false;
        imagePath = null;
        main.imageCanvas.repaint();
        main.window.setTitle("Untitled");
    }
    
    public void save() {
        if(savedImagePath == null) {
            saveAs();
        }else{
            try{
                ImageIO.write(main.imageCanvas.image, imageName.split("\\.")[1], savedImageFile);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public void Exit() {
        System.exit(0);
    }
    
    public void saveAs() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & PNG", "jpg", "gif", "png"
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
        if(savedImagePath == null && fileIsOpen == true) {
            saveAs();
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
        }
    }
}
