package com.pes.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.pes.dialog.AddMavenPropertyDialogWrapper;

public class HelloAction extends AnAction {

    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        new AddMavenPropertyDialogWrapper().showAndGet();
//        Messages.showMessageDialog(project, "Hello world!", "Greeting", Messages.getInformationIcon());
    }
}