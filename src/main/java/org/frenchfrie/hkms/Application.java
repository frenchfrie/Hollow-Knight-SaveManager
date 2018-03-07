package org.frenchfrie.hkms;

import org.frenchfrie.hkms.gui.Gui;

import javax.swing.*;

public class Application {

    public static void main(String... args) {
        Gui gui = new Gui();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);
    }

}
