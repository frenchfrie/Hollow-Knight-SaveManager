package org.frenchfrie.hkms.gui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import HKSM.data.SaveLoader;

public class Gui extends JFrame {

    private final JTextPane contentText = new JTextPane();

    private final JPanel buttonsPanel;

    private final JButton loadButton;

    private File currentSave;

    public Gui() throws HeadlessException {
        setLayout(new BorderLayout());
        add(contentText, BorderLayout.CENTER);
        contentText.setPreferredSize(new Dimension(200, 120));
        buttonsPanel = new JPanel();
        loadButton = new JButton("Reload data");
        loadButton.addActionListener(a -> loadData(currentSave));
        buttonsPanel.add(loadButton);

        JButton saveButton = new JButton();
        saveButton.addActionListener(a -> saveData(currentSave, contentText.getText()));
        buttonsPanel.add(saveButton);
        add(buttonsPanel, BorderLayout.LINE_START);

        JMenuBar menubar = new JMenuBar();
        JMenu firstMenu = new JMenu("File");
        firstMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem loadFile = new JMenuItem("Load save file", KeyEvent.VK_L);
        loadFile.addActionListener(e -> {
            File defaultSaveFolder = new File(getSaveDirectoryLocation());
            JFileChooser chooser = new JFileChooser(defaultSaveFolder);
            int fileChooserResult = chooser.showOpenDialog(this);
            if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                loadData(chooser.getSelectedFile());
            }
        });
        firstMenu.add(loadFile);

        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
        exit.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
        });
        firstMenu.add(exit);
        menubar.add(firstMenu);
        setJMenuBar(menubar);
        pack();
        setLocationRelativeTo(null);
    }

    private String getSaveDirectoryLocation() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return SystemUtils.USER_HOME + "\\AppData\\LocalLow\\Team Cherry\\Hollow Knight";
        } else if (SystemUtils.IS_OS_LINUX) {
            // Still not known by my humble self
            return SystemUtils.USER_HOME;
        } else {
            // unknown default to user space
            return SystemUtils.USER_HOME;
        }
    }

    private void loadData(File fileToLoad) {
        try {
            contentText.setText(SaveLoader.loadSave(fileToLoad).toString());
        } catch (IOException | JsonParseException e) {
            JOptionPane.showMessageDialog(this, "Could not load file, an exception happened:\n" + e.toString() + "\n" + Arrays.toString(e.getStackTrace()), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData(File fileToSave, String data) {
        try {
            SaveLoader.saveSave(fileToSave, new Gson().fromJson(data, JsonObject.class));
        } catch (IOException | JsonParseException e) {
            JOptionPane.showMessageDialog(this, "Could not save file, an exception happened:\n" + e.toString() + "\n" + Arrays.toString(e.getStackTrace()), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
