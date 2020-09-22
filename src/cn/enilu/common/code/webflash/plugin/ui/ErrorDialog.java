package cn.enilu.common.code.webflash.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by ghost on 2016/4/1.
 */
public class ErrorDialog extends DialogWrapper {
    private String msg = "";

    public ErrorDialog(@Nullable Project project) {
        super(project);
        setTitle("Error Occured, Please Retry!");
        setOKActionEnabled(false);
        init();
    }

    public ErrorDialog(@Nullable Project project, String title, String msg) {
        super(project);
        this.msg = msg;
        setTitle(title);
        setOKActionEnabled(false);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (msg.length() > 0) {
            JComponent centerPanel = super.createContentPane();
            JTextArea textArea = new JTextArea(msg,8,60);
            centerPanel.add(textArea);
            return centerPanel;
        }
        return null;
    }
}
