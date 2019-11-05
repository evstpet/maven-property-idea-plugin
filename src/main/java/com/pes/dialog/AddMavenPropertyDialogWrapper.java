package com.pes.dialog;

import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

public class AddMavenPropertyDialogWrapper extends DialogWrapper {

    public AddMavenPropertyDialogWrapper() {
        super(true); // use current window as parent
        init();
        setTitle("Add Property");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("To be continued...");
        label.setPreferredSize(new Dimension(100, 100));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
}
