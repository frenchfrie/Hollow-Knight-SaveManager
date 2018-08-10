package org.frenchfrie.hkms.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private final JTabbedPane jTabbedPane;

    public Gui() throws HeadlessException {
        setLayout(new BorderLayout());
        jTabbedPane = new JTabbedPane();
        jTabbedPane.setPreferredSize(new Dimension(800, 600));
        add(jTabbedPane);

        createAndAddMenu();
        pack();
        setLocationRelativeTo(null);
    }

    private void createAndAddMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu firstMenu = new JMenu("File");
        firstMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem loadFile = new JMenuItem("Load save file", KeyEvent.VK_L);
        loadFile.addActionListener(e -> {
            File defaultSaveFolder = new File(getSaveDirectoryLocation());
            JFileChooser chooser = new JFileChooser(defaultSaveFolder);
            int fileChooserResult = chooser.showOpenDialog(this);
            if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                try {
                    SaveEditionPanel component = new SaveEditionPanel(selectedFile);
                    jTabbedPane.addTab(null, component);
                    jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() -1, tabComponent(selectedFile.getName(), jTabbedPane, component));
                } catch (HKEditorException e1) {
                    JOptionPane.showMessageDialog(this, "Could not load file, an exception happened:\n" + e.toString() + "\n" + Arrays.toString(e1.getStackTrace()), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
    }

    private JPanel tabComponent(String title, JTabbedPane tabPane, Component tabComponent) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(title));
        JButton closeButton = new JButton("x");
        closeButton.setActionCommand("close tab");
        closeButton.addActionListener(a -> {if (a.getActionCommand().equals("close tab")) {tabPane.remove(tabComponent);}});
        panel.add(closeButton);
        return panel;
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

}
