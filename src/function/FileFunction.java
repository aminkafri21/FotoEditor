/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

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
    public FileFunction(Main main) {
        this.main = main;
    }
    
    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Image", "jpg", "gif"
        );
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(main.window);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            main.imageCanvas.repaint();
        }
    }
}
