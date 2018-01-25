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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class StatusPanel extends JPanel {

    private static final long serialVersionUID = 2132002061140599852L;

    private static final URL GIF_RUN = Thread.currentThread().getContextClassLoader().getResource("run.gif");

    private JTextPane textPaneMessages;
    private JScrollPane scrollPaneMessage;
    private JProgressBar progressBarStatus;
    private JProgressBar progressBarTotalStatus;
    private ImageIcon runImage;
    private JLabel labelIcon;
    private JLabel labelWorkingClientsField;
    private JLabel labelWorkingClients;
    private JLabel labelMaxTaskProgressField;
    private JLabel labelMaxTaskProgress;
    private JLabel labelTotalField;
    private JLabel labelMineField;
    private JPanel panelInformation;

    private boolean isProgressBarActivated;
    private boolean isTextFieldActivated;
    private boolean isProgressBarTotalActivated;

    public StatusPanel(boolean isProgressBarActivated, boolean isTextFieldActivated, Integer totalProgressBar, Integer volunteersCount) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints GridConstraints = new GridBagConstraints();

        panelInformation = new JPanel(new GridBagLayout());

        labelMaxTaskProgress = new JLabel();
        labelWorkingClients = new JLabel();

        labelMaxTaskProgressField = new JLabel();
        labelMaxTaskProgressField.setText("Total Status: ");

        labelWorkingClientsField = new JLabel();
        labelWorkingClientsField.setText("Volunteers: ");

        labelTotalField = new JLabel();
        labelTotalField.setText("Total: ");

        labelMineField = new JLabel();
        labelMineField.setText("Mine: ");

        if (isProgressBarActivated) {
            this.isProgressBarActivated = isProgressBarActivated;

            progressBarStatus = new JProgressBar();
            progressBarStatus.setMinimum(0);
            progressBarStatus.setMaximum(100);
            progressBarStatus.setValue(0);

            GridConstraints = MakeGBC(1, 0, 1, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.HORIZONTAL;
            GridConstraints.insets = new Insets(0, 0, 2, 0);
            this.add(progressBarStatus, GridConstraints);

            GridConstraints = MakeGBC(0, 0, 1, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.HORIZONTAL;
            GridConstraints.insets = new Insets(0, 0, 2, 5);
            this.add(labelMineField, GridConstraints);
        } else {

            if (GIF_RUN != null) {
                runImage = new ImageIcon(GIF_RUN);
            }
            labelIcon = new JLabel();
            labelIcon.setIcon(runImage);

            GridConstraints = MakeGBC(0, 0, 2, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.anchor = GridBagConstraints.CENTER;
            GridConstraints.fill = GridBagConstraints.NONE;
            this.add(labelIcon, GridConstraints);
        }

        if (totalProgressBar != null) {
            this.isProgressBarTotalActivated = true;

            progressBarTotalStatus = new JProgressBar();
            progressBarTotalStatus.setMinimum(0);
            progressBarTotalStatus.setMaximum(100);
            progressBarTotalStatus.setValue(totalProgressBar);

            GridConstraints = MakeGBC(1, 1, 1, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.HORIZONTAL;
            GridConstraints.insets = new Insets(0, 0, 2, 0);
            this.add(progressBarTotalStatus, GridConstraints);

            GridConstraints = MakeGBC(0, 1, 1, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.HORIZONTAL;
            GridConstraints.insets = new Insets(0, 0, 2, 5);
            this.add(labelTotalField, GridConstraints);

            labelMaxTaskProgress.setText(totalProgressBar.toString() + "%");
        } else {
            GridConstraints = MakeGBC(0, 1, 2, 1);
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.NONE;
            this.add(new JPanel(), GridConstraints);

            labelMaxTaskProgress.setText("no Information");
        }

        if (isTextFieldActivated) {
            this.isTextFieldActivated = isTextFieldActivated;

            textPaneMessages = new JTextPane();
            textPaneMessages.setEditable(false);
            scrollPaneMessage = new JScrollPane(textPaneMessages);

            GridConstraints = MakeGBC(0, 2, 3, 1);
            GridConstraints.weightx = 1.0;
            GridConstraints.weighty = 1.0;
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.BOTH;
            this.add(scrollPaneMessage, GridConstraints);
        } else {
            GridConstraints = MakeGBC(0, 2, 3, 1);
            GridConstraints.anchor = GridBagConstraints.WEST;
            GridConstraints.fill = GridBagConstraints.BOTH;
            this.add(new JPanel(), GridConstraints);
        }

        if (volunteersCount != null) {
            labelWorkingClients.setText(volunteersCount.toString());
        } else {
            labelWorkingClients.setText("no Information");
        }

        /* PANEL IMFORMATION */
        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelInformation.add(labelMaxTaskProgressField, GridConstraints);

        GridConstraints = MakeGBC(1, 0, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelInformation.add(labelMaxTaskProgress, GridConstraints);

        GridConstraints = MakeGBC(0, 1, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelInformation.add(labelWorkingClientsField, GridConstraints);

        GridConstraints = MakeGBC(1, 1, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelInformation.add(labelWorkingClients, GridConstraints);

        GridConstraints = MakeGBC(2, 0, 1, 2);
        GridConstraints.insets = new Insets(0, 10, 5, 0);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.BOTH;
        this.add(panelInformation, GridConstraints);
    }

    private GridBagConstraints MakeGBC(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(0, 0, 0, 0);

        return gbc;
    }

    public void setProgressBarMaximum(int value) {
        progressBarStatus.setMaximum(value);
    }

    public boolean isProgressBarActivated() {
        return this.isProgressBarActivated;
    }

    public boolean isTextFieldActivated() {
        return this.isTextFieldActivated;
    }

    public JProgressBar getProgressBarStatus() {
        return this.progressBarStatus;
    }

    public JTextPane getTextPaneMessages() {
        return this.textPaneMessages;
    }
}
