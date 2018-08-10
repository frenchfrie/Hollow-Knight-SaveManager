package org.frenchfrie.hkms.gui;

import HKSM.data.SaveLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SaveEditionPanel extends JPanel {

    public SaveEditionPanel(File currentSave) throws HKEditorException {

        setLayout(new BorderLayout());

        JTextPane contentText = new JTextPane();
        contentText.setPreferredSize(new Dimension(800, 600));
        contentText.setText(loadData(currentSave));
        JScrollPane contentTextContainer = new JScrollPane(contentText);
        contentTextContainer.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(contentTextContainer, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton loadButton = new JButton("Reload data");
        loadButton.addActionListener(a -> loadData(currentSave));
        buttonsPanel.add(loadButton);

        JButton saveButton = new JButton("Save data");
        saveButton.addActionListener(a -> saveData(currentSave, contentText.getText()));
        buttonsPanel.add(saveButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private String loadData(File fileToLoad) {
        try {
            JsonObject jsonObject = SaveLoader.loadSave(fileToLoad);
            return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
        } catch (IOException | JsonParseException e) {
            throw new HKEditorException(e);
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
