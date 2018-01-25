/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author thomas_r
 */
import client.TaskStatusListenerImpl;
import java.awt.Color;

public class TaskStatusListenerImplGui extends TaskStatusListenerImpl implements ITaskStatusGui {

    private StatusPanel statusPanel;

    public TaskStatusListenerImplGui(StatusPanel panel) {
        this.statusPanel = panel;
    }

    @Override
    public void clear() {
        if (statusPanel.isTextFieldActivated()) {
            statusPanel.getTextPaneMessages().setText("");
        }
    }

    @Override
    public void print(Object obj) {
        if (statusPanel.isTextFieldActivated()) {
            String msg = statusPanel.getTextPaneMessages().getText();
            statusPanel.getTextPaneMessages().setText(msg + " " + obj.toString());
        }
    }

    @Override
    public void println(Object obj) {
        if (statusPanel.isTextFieldActivated()) {
            String msg = statusPanel.getTextPaneMessages().getText();
            statusPanel.getTextPaneMessages().setText(msg + " " + obj.toString() + "\n");
        }
    }

    public void setBackgroundColor(Color c) {
        if (statusPanel.isTextFieldActivated()) {
            statusPanel.getTextPaneMessages().setBackground(c);
        }
    }

    public void setForegroundColor(Color c) {
        if (statusPanel.isTextFieldActivated()) {
            statusPanel.getTextPaneMessages().setForeground(c);
        }
    }

    public void setProgress(int progress) {
        if (statusPanel.isProgressBarActivated()) {
            statusPanel.getProgressBarStatus().setValue(progress);

            if (progress < 1) {
                statusPanel.updateUI();
            }
        }
    }

    void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }

    StatusPanel getStatusPanel() {
        return statusPanel;
    }

}
