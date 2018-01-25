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
import client.VMVersionCheck;
import client.GridClient;
import client.TaskSolverThread;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import interfaces.IRemoteConnection;
import java.util.HashMap;

public class ClientGui extends javax.swing.JFrame implements Observer {

    private static final long serialVersionUID = -6573446843126606577L;

    protected static final Logger LOG = Logger.getLogger(ClientGui.class.getName());

    private static final URL GIF_SUCCESS = Thread.currentThread().getContextClassLoader().getResource("success.gif");
    private static final URL GIF_WARNING = Thread.currentThread().getContextClassLoader().getResource("warning.gif");
    private static final URL GIF_ERROR = Thread.currentThread().getContextClassLoader().getResource("error.gif");
    private static final URL ICON_APP = Thread.currentThread().getContextClassLoader().getResource("icon.png");

    static ClientGui ui;

    private JButton btnRunTask;
    private JButton btnAbortTask;
    private JButton btnRefreshTasks;
    private JButton btnAboutUs;

    private JScrollPane scrollPaneTaskDescription;
    private JScrollPane scrollPaneTaskList;
    private JScrollPane scrollPaneLog;

    private JList listTasks;

    private JTextField textFieldMessages;
    private JTextArea textAreaDescription;
    private JTextPane textPaneLog;

    private JPanel panelTaskList;
    private JPanel panelTaskStatus;
    private JPanel panelButtons;
    private JPanel panelInformation;
    private JPanel panelMessages;
    private JPanel panelMainTab;
    private JPanel panelExtTab;

    private JLabel labelJavaVersion;
    private JLabel labelClientID;
    private JLabel labelIcon;

    private ImageIcon icon;
    private Style styleText;

    private TaskStatusListenerImplGui taskStatus;
    private StatusPanel statusPanel;
    private JTabbedPane tabPanel;

    // DEKLARATION MODELS
    DefaultListModel modelTaskList;

    // GRID OBJECTS
    private String uniqueTaskName;
    private final IRemoteConnection rc;
    private TaskSolverThread taskSolver;

    public ClientGui(IRemoteConnection rc) {
        super();
        ui = this;

        this.setTitle("GRID CLIENT 0.1");
        this.setSize(700, 300);
        this.setMinimumSize(new Dimension(300, 500));
        this.setLocation(200, 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.rc = rc;
        this.addWindowListener(new WindowListener());

        // setzt das Icon der Applikation
        if (ICON_APP != null) {
            this.setIconImage((new ImageIcon(ICON_APP)).getImage());
        }

        btnRunTask = new JButton("run Task");
        btnRunTask.setSize(40, 20);
        btnRunTask.addActionListener(new ButtonClicks());
        btnRunTask.setEnabled(false);

        btnAbortTask = new JButton("abort Task");
        btnAbortTask.setSize(40, 20);
        btnAbortTask.addActionListener(new ButtonClicks());
        btnAbortTask.setEnabled(false);

        btnRefreshTasks = new JButton("refresh List");
        btnRefreshTasks.setSize(40, 20);
        btnRefreshTasks.addActionListener(new ButtonClicks());

        btnAboutUs = new JButton("about");
        btnAboutUs.setSize(40, 20);
        btnAboutUs.addActionListener(new ButtonClicks());

        labelIcon = new JLabel();

        textAreaDescription = new JTextArea();
        textAreaDescription.setEditable(false);

        textFieldMessages = new JTextField();
        textFieldMessages.setEditable(false);

        textPaneLog = new JTextPane();
        textPaneLog.setEditable(false);
        styleText = textPaneLog.addStyle("gray normal", null);
        StyleConstants.setForeground(styleText, Color.red);

        labelJavaVersion = new JLabel();
        labelClientID = new JLabel("Your Client ID:   " + GridClient.getClientId());
        labelClientID.setFont(new Font("Serif", Font.PLAIN, 10));

        loadTaskList();

        // LISTE ANLEGEN UND MODEL UEBERGEBEN
        listTasks = new JList(modelTaskList);
        listTasks.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listTasks.setLayoutOrientation(JList.VERTICAL);
        listTasks.setVisibleRowCount(-1);
        listTasks.addListSelectionListener(new ListSelectionHandler());

        // SCROLLPANES ANLEGEN UND ZUWEISEN
        scrollPaneTaskList = new JScrollPane(listTasks);
        scrollPaneTaskDescription = new JScrollPane(textAreaDescription);
        scrollPaneLog = new JScrollPane(textPaneLog);

        // PANELS INSTANZIIEREN
        panelTaskList = new JPanel();
        panelTaskStatus = new JPanel();
        panelButtons = new JPanel();
        panelInformation = new JPanel();
        panelMessages = new JPanel();
        panelMainTab = new JPanel();
        panelExtTab = new JPanel();

        tabPanel = new JTabbedPane();

        GridBagConstraints GridConstraints = new GridBagConstraints();

        /* panelInformation */
        panelInformation.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 2);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.NONE;
        panelInformation.add(labelIcon, GridConstraints);

        GridConstraints = MakeGBC(1, 0, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.NONE;
        GridConstraints.weightx = 1.0;
        panelInformation.add(labelJavaVersion, GridConstraints);

        GridConstraints = MakeGBC(1, 1, 1, 1);
        GridConstraints.anchor = GridBagConstraints.WEST;
        GridConstraints.fill = GridBagConstraints.NONE;
        GridConstraints.weightx = 1.0;
        panelInformation.add(labelClientID, GridConstraints);

        /* panelTaskList */
        panelTaskList.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 2);
        GridConstraints.weightx = 0.3;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.WEST;
        panelTaskList.add(scrollPaneTaskList, GridConstraints);

        GridConstraints = MakeGBC(1, 0, 1, 1);
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.WEST;
        panelTaskList.add(panelInformation, GridConstraints);

        GridConstraints = MakeGBC(1, 1, 1, 1);
        GridConstraints.weightx = 0.7;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.WEST;
        panelTaskList.add(scrollPaneTaskDescription, GridConstraints);

        GridConstraints = MakeGBC(0, 2, 1, 1);
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        GridConstraints.anchor = GridBagConstraints.CENTER;
        panelTaskList.add(btnRefreshTasks, GridConstraints);

        GridConstraints = MakeGBC(1, 2, 1, 1);
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.WEST;
        panelTaskList.add(textFieldMessages, GridConstraints);

        /* panelButtons */
        panelButtons.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.anchor = GridBagConstraints.NORTH;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelButtons.add(btnRunTask, GridConstraints);

        GridConstraints = MakeGBC(0, 1, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.anchor = GridBagConstraints.NORTH;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelButtons.add(btnAbortTask, GridConstraints);

        GridConstraints = MakeGBC(0, 1, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.anchor = GridBagConstraints.SOUTH;
        GridConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelButtons.add(btnAboutUs, GridConstraints);

        /* panelMessages */
        panelMessages.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.WEST;
        panelMessages.add(scrollPaneLog, GridConstraints);

        /* panelMainTab */
        panelMainTab.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.NORTHWEST;
        GridConstraints.insets = new Insets(5, 5, 5, 5);
        panelMainTab.add(panelTaskList, GridConstraints);

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.NORTHWEST;
        GridConstraints.insets = new Insets(5, 5, 5, 5);
        panelMainTab.add(panelTaskStatus, GridConstraints);

        GridConstraints = MakeGBC(1, 0, 1, 1);
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.NORTHEAST;
        GridConstraints.insets = new Insets(5, 5, 5, 5);
        panelMainTab.add(panelButtons, GridConstraints);

        panelExtTab.setOpaque(true);
        tabPanel.addTab("Main", panelMainTab);

        /* panelExtTab */
        panelExtTab.setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.NORTHEAST;
        GridConstraints.insets = new Insets(5, 5, 5, 5);
        panelExtTab.add(panelMessages, GridConstraints);

        panelExtTab.setOpaque(true);
        tabPanel.addTab("Extendet", panelExtTab);

        /* this */
        this.getContentPane().setLayout(new GridBagLayout());

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.fill = GridBagConstraints.BOTH;
        GridConstraints.anchor = GridBagConstraints.NORTHEAST;
        GridConstraints.insets = new Insets(0, 0, 0, 0);
        this.getContentPane().add(tabPanel, GridConstraints);

        this.setVisible(true);
    }

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ButtonClicks implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnRunTask) {
                int taskIndex = listTasks.getSelectedIndex();
                uniqueTaskName = (String) modelTaskList.getElementAt(taskIndex);

                if (uniqueTaskName != null) {
                    taskSolver = new TaskSolverThread(rc, ui);

                    try {
                        if (!rc.isTaskFinished(uniqueTaskName)) {
                            taskSolver.startTask(uniqueTaskName);

                            btnAbortTask.setEnabled(true);
                            btnRunTask.setEnabled(false);

                            panelTaskList.setVisible(false);
                            panelTaskStatus.setVisible(true);

                            textFieldMessages.setForeground(Color.darkGray);
                            textFieldMessages.setText("Task in Ausführung...");
                        } else {
                            textFieldMessages.setForeground(Color.green);
                            textFieldMessages.setText("Task bereits abgeschlossen, Taskliste wurde neu geladen...");

                            BtnRefreshTaskClicked();
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (e.getSource() == btnAbortTask) {
                taskSolver.abortTask();

                btnAbortTask.setEnabled(false);
                btnRunTask.setEnabled(true);

                panelTaskList.setVisible(true);
                panelTaskStatus.setVisible(false);

                textFieldMessages.setForeground(Color.red);
                textFieldMessages.setText("Task abgebrochen!");
            }

            if (e.getSource() == btnRefreshTasks) {
                BtnRefreshTaskClicked();
            }

            if (e.getSource() == btnAboutUs) {
                JOptionPane.showMessageDialog(ui,
                        "GridArchitecture\nVersion 0.9\n\n"
                        + "http://nopage.de/\n\nCopyright (c) 2004 M.Loos, T.Rothe, A.Schulz",
                        "About", JOptionPane.OK_OPTION,
                        null);
            }
        }
    }

    private class ListSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                int iIndex = listTasks.getSelectedIndex();

                if (iIndex != -1) {
                    String strCurrentTask = (String) modelTaskList.getElementAt(iIndex);
                    if (strCurrentTask.equals("NO TASK FOUND")) {
                        textAreaDescription.setText("no task found on Server!");
                    } else {
                        String strTaskDescription = rc.getTaskDescription(strCurrentTask);
                        textAreaDescription.setText(strTaskDescription);

                        Boolean bVersionCheck = false;

                        try {

                            LOG.log(Level.INFO, "Task Java Version <{0}>", rc.getJavaVersion(strCurrentTask));

                            bVersionCheck = VMVersionCheck.isCompatibleVersion(rc.getJavaVersion(strCurrentTask));
                        } catch (NoSuchMethodError | Exception ex) {
                            bVersionCheck = null;
                        }

                        if (bVersionCheck == true) {
                            if (GIF_SUCCESS != null) {
                                icon = new ImageIcon(GIF_SUCCESS);
                            }
                            labelIcon.setIcon(icon);
                            labelJavaVersion.setText("Dieser Task ist kompatibel zu ihrer JavaVersion!");

                            btnRunTask.setEnabled(true);
                        } else if (bVersionCheck == null) {
                            if (GIF_WARNING != null) {
                                icon = new ImageIcon(GIF_WARNING);
                            }
                            labelIcon.setIcon(icon);
                            labelJavaVersion
                                    .setText("Dieser Task ist möglicherweise nicht kompatibel zu ihrer JavaVersion!");

                            btnRunTask.setEnabled(true);
                        } else {
                            if (GIF_ERROR != null) {
                                icon = new ImageIcon(GIF_ERROR);
                            }
                            labelIcon.setIcon(icon);
                            labelJavaVersion.setText("Dieser Task ist nicht kompatibel zu ihrer JavaVersion!");
                        }
                    }
                }
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        }
    }

    private class WindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent arg0) {
            if (taskSolver != null) {
                taskSolver.abortTask();
            }

            super.windowClosed(arg0);
            System.exit(0);
        }
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

    public void createStatusPanel(Boolean isProgressBarActivated_, Boolean isTextFieldActivated_,
            Integer maxProgressValue_, Integer totalProgressBar_, Integer volunteersCount_) {
        if (isProgressBarActivated_ == null) {
            isProgressBarActivated_ = false;
        }
        if (isTextFieldActivated_ == null) {
            isTextFieldActivated_ = false;
        }
        if (maxProgressValue_ == null) {
            maxProgressValue_ = 100;
        }

        if (taskStatus != null) {
            panelTaskStatus.remove(taskStatus.getStatusPanel());
        }

        statusPanel = new StatusPanel(isProgressBarActivated_, isTextFieldActivated_, totalProgressBar_,
                volunteersCount_);
        statusPanel.setProgressBarMaximum(maxProgressValue_);

        taskStatus = new TaskStatusListenerImplGui(statusPanel);
        panelTaskStatus.setLayout(new GridBagLayout());

        GridBagConstraints GridConstraints = new GridBagConstraints();

        GridConstraints = MakeGBC(0, 0, 1, 1);
        GridConstraints.weightx = 1.0;
        GridConstraints.weighty = 1.0;
        GridConstraints.insets = new Insets(5, 0, 0, 0);
        GridConstraints.anchor = GridBagConstraints.CENTER;
        GridConstraints.fill = GridBagConstraints.BOTH;
        panelTaskStatus.add(taskStatus.getStatusPanel(), GridConstraints);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        Date dateCurrentTime = new Date();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        String strTemp = textPaneLog.getText() + "\n" + df.format(dateCurrentTime);

        if (arg instanceof Boolean) {
            Boolean b = (Boolean) arg;

            if (b) {
                StyleConstants.setForeground(styleText, Color.green);
                strTemp = strTemp + "Erfolg... Task abgearbeitet";
                textPaneLog.setText(strTemp);
                textFieldMessages.setForeground(Color.green);
                textFieldMessages.setText("Task abgeschlossen!");

                panelTaskList.setVisible(true);
                panelTaskStatus.setVisible(false);

                btnAbortTask.setEnabled(false);

                BtnRefreshTaskClicked();
            }
        } else if (arg instanceof HashMap) {
            HashMap<String, Object> htArguments = (HashMap<String, Object>) arg;

            Boolean bIsProgressBarActivated = (Boolean) htArguments.get("IsProgressBarActivated");
            Boolean bIsTextFieldActivated = (Boolean) htArguments.get("IsTextFieldActivated");
            Integer iTotalProgressBar = (Integer) htArguments.get("TaskProgress");
            Integer iVolunteersCount = (Integer) htArguments.get("VolunteersCount");
            Integer iMaxProgressValue = (Integer) htArguments.get("MaxProgressValue");

            // StatusPanel für den Task neu zeichnen
            createStatusPanel(bIsProgressBarActivated, bIsTextFieldActivated, iMaxProgressValue, iTotalProgressBar,
                    iVolunteersCount);
        } else if (arg instanceof RemoteException) {
            StyleConstants.setForeground(styleText, Color.red);
            strTemp = strTemp + "Fehler: Die Verbindung zum Server konnte nicht hergestellt werden: " + arg;
            textPaneLog.setText(strTemp);
            textFieldMessages.setForeground(Color.red);
            textFieldMessages.setText("Verbindungsfehler!");

            tabPanel.setSelectedIndex(1);

            panelTaskList.setVisible(true);
            panelTaskStatus.setVisible(false);

            btnAbortTask.setEnabled(false);

            BtnRefreshTaskClicked();
        } else if (arg instanceof ClassNotFoundException) {
            StyleConstants.setForeground(styleText, Color.red);
            strTemp = strTemp + "Fehler: Eine benötigte Klasse konnte nicht geladen werden: " + arg;
            textPaneLog.setText(strTemp);
            textFieldMessages.setForeground(Color.red);
            textFieldMessages.setText("Interner Fehler!");

            tabPanel.setSelectedIndex(1);

            panelTaskList.setVisible(true);
            panelTaskStatus.setVisible(false);

            btnAbortTask.setEnabled(false);

            BtnRefreshTaskClicked();
        } else {
            StyleConstants.setForeground(styleText, Color.blue);
            strTemp = strTemp + (String) arg;
            textPaneLog.setText(strTemp);
        }
    }

    public void BtnRefreshTaskClicked() {
        textAreaDescription.setText("");
        loadTaskList();
        btnRunTask.setEnabled(false);
    }

    public void loadTaskList() {
        if (modelTaskList == null) {
            modelTaskList = new DefaultListModel();
        } else {
            modelTaskList.clear();
        }

        try {
            if (rc.getTaskList().length != 0) {
                for (String s : rc.getTaskList()) {
                    if (!rc.isTaskFinished(s)) {
                        modelTaskList.addElement(s);
                    }
                }

                if (GIF_WARNING != null) {
                    icon = new ImageIcon(GIF_WARNING);
                }
                labelIcon.setIcon(icon);
                labelJavaVersion.setText("Waiting...");
            } else {
                if (GIF_WARNING != null) {
                    icon = new ImageIcon(GIF_ERROR);
                }
                labelIcon.setIcon(icon);
                modelTaskList.addElement("NO TASK FOUND");
                labelJavaVersion.setText("Error!");
            }
        } catch (RemoteException re) {

            re.printStackTrace();
        }
    }

    public TaskStatusListenerImplGui getTaskStatus() {
        return taskStatus;
    }
}
