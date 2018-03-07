package org.frenchfrie.hkms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Gui extends JFrame {

    private final JTextPane contentText = new JTextPane();

    private final JPanel buttonsPanel;

    private final JButton loadButton;

    public Gui() throws HeadlessException {
        setLayout(new BorderLayout());
        add(contentText, BorderLayout.CENTER);
        contentText.setPreferredSize(new Dimension(200, 120));
        buttonsPanel = new JPanel();
        loadButton = new JButton("Load data");
        loadButton.addActionListener(a -> {
            contentText.setText("Salut le monde !");
        });
        buttonsPanel.add(loadButton);
        add(buttonsPanel, BorderLayout.LINE_START);

        JMenuBar menubar = new JMenuBar();
        JMenu firstMenu = new JMenu("File");
        firstMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem loadFile = new JMenuItem("Load save file", KeyEvent.VK_L);
        loadFile.addActionListener(e -> {
            String userName = System.getProperty("user.name");
            File defaultSaveFolder = new File("C:\\Users\\" + userName + "\\AppData\\LocalLow\\Team Cherry\\Hollow Knight");
            JFileChooser chooser = new JFileChooser(defaultSaveFolder);
            chooser.showOpenDialog(this);
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
}
