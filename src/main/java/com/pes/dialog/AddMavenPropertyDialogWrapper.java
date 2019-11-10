package com.pes.dialog;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.ui.JBUI;
import lombok.Getter;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

@Getter
public class AddMavenPropertyDialogWrapper extends DialogWrapper {

    private JComboBox<PropertiesNames> nameComboBox;
    private JTextField valueTxt;

    public AddMavenPropertyDialogWrapper() {
        super(true);
        init();
        setTitle("Add Property");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        dialogPanel.add(withBorders(createLabelsPanel()), BorderLayout.WEST);
        dialogPanel.add(withBorders(createInputsPanel()), BorderLayout.EAST);

        return dialogPanel;
    }

    private JPanel createLabelsPanel() {
        JPanel labelsPanel = new JPanel(new BorderLayout());

        JLabel nameLabel = new JLabel("name");
        JLabel valueLabel = new JLabel("value");

        labelsPanel.add(nameLabel, BorderLayout.NORTH);
        labelsPanel.add(valueLabel, BorderLayout.SOUTH);

        return labelsPanel;
    }

    private JPanel createInputsPanel() {
        JPanel inputsPanel = new JPanel(new BorderLayout());

        nameComboBox = new ComboBox<>(PropertiesNames.values());
        valueTxt = new JTextField();

        inputsPanel.add(nameComboBox, BorderLayout.NORTH);
        inputsPanel.add(valueTxt, BorderLayout.SOUTH);

        return inputsPanel;
    }

    @Getter
    private enum PropertiesNames {
        MAVEN_COMPILER_SOURCE("maven.compiler.source"),
        MAVEN_COMPILER_TARGET("maven.compiler.target"),
        PROJECT_BUILD_SOURCE_ENCODING("project.build.sourceEncoding"),
        PROJECT_REPORTING_OUTPUT_ENCODING("project.reporting.outputEncoding");

        private String name;

        PropertiesNames(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private JPanel withBorders(JPanel panel) {
        panel.setBorder(JBUI.Borders.empty(10));

        return panel;
    }
}
