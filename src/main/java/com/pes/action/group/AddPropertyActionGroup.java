package com.pes.action.group;

import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.psi.PsiFile;

public class AddPropertyActionGroup extends DefaultActionGroup {
    private static final String POM = "pom";

    @Override
    public void update(AnActionEvent e) {
        if (ActionPlaces.isPopupPlace(e.getPlace())) {
            boolean isPOM = isPom(e.getData(CommonDataKeys.PSI_FILE));
            e.getPresentation().setEnabledAndVisible(isPOM);
        }
    }

    private boolean isPom(PsiFile psiFile) {
        if (psiFile != null) {
            return XMLLanguage.INSTANCE.isKindOf(psiFile.getLanguage())
                    && POM.equals(getFileNameWithoutExtension(psiFile.getName()));
        }

        return false;
    }

    private String getFileNameWithoutExtension(String fullFileName) {
        return fullFileName.substring(0, fullFileName.indexOf('.'));
    }
}
