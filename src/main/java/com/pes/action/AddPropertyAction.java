package com.pes.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.pes.dialog.AddMavenPropertyDialogWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.intellij.openapi.application.ApplicationManager.getApplication;

public class AddPropertyAction extends AnAction {

    public AddPropertyAction() {
        super("Add Maven Property...");
    }

    public void actionPerformed(@NotNull AnActionEvent e) {
        AddMavenPropertyDialogWrapper addMavenPropertyDialogWrapper = new AddMavenPropertyDialogWrapper();
        if (addMavenPropertyDialogWrapper.showAndGet()) {
            String name = addMavenPropertyDialogWrapper.getNameComboBox().getSelectedItem().toString();
            String value = addMavenPropertyDialogWrapper.getValueTxt().getText();

            XmlFile data = (XmlFile) e.getData(CommonDataKeys.PSI_FILE);

            XmlTag properties = lookUpPropertiesTag(data, e);

            if (properties != null) {
                CommandProcessor.getInstance().executeCommand(
                        e.getProject(),
                        () -> getApplication().runWriteAction(() -> {
                            XmlTag property = properties.createChildTag(name,
                                                                        properties.getNamespace(),
                                                                        value,
                                                                        false);
                            properties.addSubTag(property, false);
                        }),
                        "Insert maven property int POM",
                        null
                );
            }
        }
    }

    private XmlTag lookUpPropertiesTag(XmlFile file, AnActionEvent e) {
        XmlTag properties = findPropertiesTag(file);

        if (properties == null) {
            properties = createPropertiesTag(file, e);
        }

        return properties;
    }

    private XmlTag findPropertiesTag(XmlFile file) {
        return getXmlDocumentSafety(file)
                .map(XmlDocument::getRootTag)
                .map(rootTag -> rootTag.findFirstSubTag("properties"))
                .orElse(null);
    }

    private XmlTag createPropertiesTag(XmlFile file, AnActionEvent e) {
        getXmlDocumentSafety(file)
                .map(XmlDocument::getRootTag)
                .ifPresent(rootTag -> {
                    CommandProcessor.getInstance().executeCommand(
                            e.getProject(),
                            () -> getApplication().runWriteAction(() -> {
                                XmlTag properties = rootTag.createChildTag("properties",
                                                                           rootTag.getNamespace(),
                                                                           null,
                                                                           false);
                                rootTag.addSubTag(properties, false);
                            }),
                            "Insert properties tag int POM",
                            null
                    );
                });

        return findPropertiesTag(file);
    }

    private Optional<XmlDocument> getXmlDocumentSafety(XmlFile file) {
        return Optional.ofNullable(file).map(xmlFile -> file.getDocument());
    }
}