package org.frenchfrie.hkms;

import org.frenchfrie.hkms.gui.Gui;
import org.slf4j.Logger;

import javax.swing.*;

import static org.slf4j.LoggerFactory.getLogger;

public class Application {

    private static final Logger log = getLogger(Application.class);
    
    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            log.debug("Could not set system native look and feel.");
        }
        Gui gui = new Gui();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);
    }

}
