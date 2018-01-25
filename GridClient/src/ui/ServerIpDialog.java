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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class ServerIpDialog extends JDialog {

    private static final long serialVersionUID = 3784596401777053801L;

    private InetAddress ipAddressFromServer;
    private JTextField textFieldIP1;
    private JLabel labelMessage, labelError;
    private JButton btnOk, btnCancel;
    private JPanel panelButtons;

    protected int returnValue;

    public ServerIpDialog() {
        super();
        this.setModal(true);
        this.setTitle("GridServer IP");
        this.setSize(300, 150);
        this.setLocation(200, 50);
        this.setLayout(new GridBagLayout());
        this.setMinimumSize(new Dimension(300, 150));
        this.setMaximumSize(new Dimension(300, 150));

        InitialiseComponents();

        // GridBagLayout anlegen
        GridBagConstraints gridContriants = new GridBagConstraints();

        panelButtons.setLayout(new GridBagLayout());

        gridContriants = MakeGBC(0, 0, 1, 1);
        gridContriants.insets = new Insets(0, 0, 0, 0);
        gridContriants.fill = GridBagConstraints.HORIZONTAL;
        gridContriants.anchor = GridBagConstraints.CENTER;
        panelButtons.add(btnOk, gridContriants);

        gridContriants = MakeGBC(1, 0, 1, 1);
        gridContriants.insets = new Insets(0, 10, 0, 0);
        gridContriants.fill = GridBagConstraints.HORIZONTAL;
        gridContriants.anchor = GridBagConstraints.CENTER;
        panelButtons.add(btnCancel, gridContriants);

        this.getContentPane().setLayout(new GridBagLayout());

        gridContriants = MakeGBC(0, 0, 1, 1);
        gridContriants.weightx = 1.0;
        gridContriants.weighty = 1.0;
        gridContriants.fill = GridBagConstraints.HORIZONTAL;
        gridContriants.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(labelMessage, gridContriants);

        gridContriants = MakeGBC(0, 1, 1, 1);
        gridContriants.weightx = 1.0;
        gridContriants.weighty = 1.0;
        gridContriants.fill = GridBagConstraints.HORIZONTAL;
        gridContriants.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(textFieldIP1, gridContriants);

        gridContriants = MakeGBC(0, 2, 1, 1);
        gridContriants.weightx = 1.0;
        gridContriants.weighty = 1.0;
        gridContriants.fill = GridBagConstraints.HORIZONTAL;
        gridContriants.anchor = GridBagConstraints.CENTER;
        this.getContentPane().add(labelError, gridContriants);

        gridContriants = MakeGBC(0, 3, 1, 1);
        gridContriants.weightx = 1.0;
        gridContriants.weighty = 1.0;
        gridContriants.fill = GridBagConstraints.NONE;
        gridContriants.anchor = GridBagConstraints.EAST;
        this.getContentPane().add(panelButtons, gridContriants);
    }

    private void InitialiseComponents() {
        labelError = new JLabel();
        labelMessage = new JLabel();
        labelMessage.setText("Server IP:");

        textFieldIP1 = new JTextField();
        panelButtons = new JPanel();

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ButtonClicks());

        btnOk = new JButton("   Ok   ");
        btnOk.addActionListener(new ButtonClicks());

        InputMap inmap = btnOk.getInputMap();
        KeyStroke enterPressed = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        KeyStroke enterReleased = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);
        inmap.put(enterPressed, "pressed");
        inmap.put(enterReleased, "released");
        btnOk.setInputMap(JComponent.WHEN_FOCUSED, inmap);

        inmap = btnCancel.getInputMap();
        KeyStroke cancelPressed = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        KeyStroke cancelReleased = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
        inmap.put(cancelPressed, "pressed");
        inmap.put(cancelReleased, "released");
        btnCancel.setInputMap(JComponent.WHEN_FOCUSED, inmap);
    }

    class ButtonClicks implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnOk) {
                if (textFieldIP1.getText().isEmpty()) {
                    labelError.setText("IP is required!");
                } else {
                    try {
                        labelError.setText("");
                        ipAddressFromServer = InetAddress.getByName(textFieldIP1.getText());

                        ServerIpDialog.this.setVisible(false);
                        returnValue = JOptionPane.OK_OPTION;
                    } catch (UnknownHostException ex) {
                        labelError.setText("No valid IP address!");
                    }
                }
            }
            if (e.getSource() == btnCancel) {
                ipAddressFromServer = null;
                ServerIpDialog.this.setVisible(false);
                returnValue = JOptionPane.CANCEL_OPTION;
            }
        }
    }

    public InetAddress getValue() {
        return this.ipAddressFromServer;
    }

    private GridBagConstraints MakeGBC(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.insets = new Insets(5, 5, 0, 5);

        return gbc;
    }

    public void setErrorMessage(String strError_) {
        labelError.setText(strError_);
    }

    public InetAddress getInetAddress() {
        return ipAddressFromServer;
    }
}
