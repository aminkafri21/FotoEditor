package main;

import function.EditFunction;
import function.FileFunction;
import function.ImageFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Main implements ActionListener {

    public JFrame window;
    JMenuBar menubar;
    JMenu mFile, mEdit, mImage;
    JMenuItem iNew, iOpen, iExit, iSave, iSaveAs;
    JMenuItem iUndo, iCropping, iResize, iSelect;
    JMenuItem iBrightness, iBlur, iMono;
    
    public ImageCanvas imageCanvas;
    public FileFunction fileFunc;
    public ImageFunction imageFunc;
    public EditFunction editFunc;
    public Stack<BufferedImage> editedImage;
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        createWindow();
        createMenuBar();
        createMenu();
        createMenuItem();

        fileFunc = new FileFunction(this);
        imageCanvas = new ImageCanvas(this);
        imageFunc = new ImageFunction(this);
        editFunc = new EditFunction(this);
        editedImage = new Stack<>();
        window.add(imageCanvas);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void createWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("FotoEditor");
        window.setSize(new Dimension(800, 600));

    }

    public void createMenuBar() {
        menubar = new JMenuBar();
        window.setJMenuBar(menubar);
    }

    public void createMenu() {
        mFile = new JMenu("File");
        menubar.add(mFile);

        mEdit = new JMenu("Edit");
        menubar.add(mEdit);

        mImage = new JMenu("Image");
        menubar.add(mImage);
    }

    public void createMenuItem() {
        // File Menu Item
        iNew = new JMenuItem("New");
        iNew.addActionListener(this);
        iNew.setActionCommand("New");
        mFile.add(iNew);

        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        mFile.add(iOpen);

        iExit = new JMenuItem("Exit");
        iExit.addActionListener(this);
        iExit.setActionCommand("Exit");
        mFile.add(iExit);

        iSave = new JMenuItem("Save");
        iSave.addActionListener(this);
        iSave.setActionCommand("Save");
        mFile.add(iSave);

        iSaveAs = new JMenuItem("Save As");
        iSaveAs.addActionListener(this);
        iSaveAs.setActionCommand("SaveAs");
        mFile.add(iSaveAs);

        // End

        //Edit Menu Item
        iUndo = new JMenuItem("Undo");
        iUndo.addActionListener(this);
        iUndo.setActionCommand("Undo");
        mEdit.add(iUndo);

        iCropping = new JMenuItem("Crop");
        iCropping.addActionListener(this);
        iCropping.setActionCommand("Crop");
        mEdit.add(iCropping);

        iResize = new JMenuItem("Resize");
        iResize.addActionListener(this);
        iResize.setActionCommand("Resize");
        mEdit.add(iResize);

        iSelect = new JMenuItem("Select");
        iSelect.addActionListener(this);
        iSelect.setActionCommand("Select");
        mEdit.add(iSelect);
        //End

        //Image Item
        iBrightness = new JMenuItem("Brightness Adjustment");
        iBrightness.addActionListener(this);
        iBrightness.setActionCommand("Bright");
        mImage.add(iBrightness);

        iBlur = new JMenuItem("Blur");
        iBlur.addActionListener(this);
        iBlur.setActionCommand("Blur");
        mImage.add(iBlur);

        iMono = new JMenuItem("Monochrome Image");
        iMono.addActionListener(this);
        iMono.setActionCommand("Mono");
        mImage.add(iMono);
        //End
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); 
        switch(command){
        case "New":
            fileFunc.New();
            break;
        case "Open":
            fileFunc.open();
            break; 
        case "Exit":
            fileFunc.Exit();
            break; 
        case "Save":
            fileFunc.save();
            break;
        case "SaveAs":
            fileFunc.saveAs();
            break;
        case "Undo":
            editFunc.undo();
            break;
        case "Crop":
            editFunc.crop();
            break;
        case "Resize":
            imageCanvas.image = editFunc.resize();
            imageCanvas.repaint();
            break;
        case "Select":
            editFunc.select();
            break;
        case "Bright":
            imageFunc.adjustBrightness();
            break;
        case "Blur":
            imageFunc.adjustBlur();
            break;
        case "Mono":
            imageFunc.setMono();
            imageCanvas.repaint();
            break;
        }
    }
}
