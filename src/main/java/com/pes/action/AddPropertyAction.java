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

            XmlTag properties = getXmlDocumentSafety(data)
                    .map(XmlDocument::getRootTag)
                    .map(rootTag -> rootTag.findFirstSubTag("properties"))
                    .orElseGet(() -> {
                        System.out.println("No properties tag found");
                        return null;
                    });

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
                        "Insert maven property",
                        null
                );
            }
        }
    }

    private Optional<XmlDocument> getXmlDocumentSafety(XmlFile file) {
        return Optional.ofNullable(file).map(xmlFile -> file.getDocument());
    }
}