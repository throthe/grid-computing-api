package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
import util.UserTask;
import java.lang.Thread.State;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import ui.ClientGui;
import interfaces.IRemoteConnection;
import interfaces.ITaskStatusListener;

public class TaskSolverThread extends Observable implements Runnable {

    protected final static Logger LOG = Logger.getLogger(TaskSolverThread.class.getName());

    private final IRemoteConnection rc;
    private final Observer observer;

    private UserTask task;
    private Thread taskSolver;
    private RMIClassLoader taskClassLoader;

    public TaskSolverThread(IRemoteConnection rc, Observer obs) {
        this.rc = rc;
        this.observer = obs;
    }

    @Override
    public void run() {
        if (task != null) {
            try {
                logAndNotifyHelper("Loading classes required by current Task...");
                taskClassLoader.loadTaskClasses();
                HashMap<String, Object> params;
                logAndNotifyHelper("Receiving parameters for work......");

                while ((params = rc.loadParams(GridClient.getClientId())) != null) {
                    try {
                        logAndNotifyHelper("Injecting parameters into current Task...");
                        task.getBackEnd().injectParameters(params);
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Injecting parameters into current task failed!");
                        System.out.println(e.toString());
                    }

                    HashMap<String, Object> guiParams = new HashMap<>();
                    guiParams.put("IsProgressBarActivated", task.isProgressBarRequired());
                    guiParams.put("IsTextFieldActivated", task.isTextOutputAreaRequired());
                    guiParams.put("MaxProgressValue", task.getMaxProgressValue());

                    Integer volunteersCount = rc.getNumberOfVolunteers(task.getUniqueTaskName());

                    if (volunteersCount != null) {
                        guiParams.put("VolunteersCount", volunteersCount);
                    }

                    if (rc.isTaskAcceptStatusQuery(task.getUniqueTaskName())) {
                        Integer taskProgress = rc.getTaskProgress(task.getUniqueTaskName());

                        if (taskProgress != null) {
                            guiParams.put("TaskProgress", taskProgress);
                        }
                    }

                    notifyObserver(guiParams);

                    task.getBackEnd().setStatusObserver((ITaskStatusListener) ((ClientGui) observer).getTaskStatus());
                    logAndNotifyHelper("---start partial work---");
                    task.run();
                    logAndNotifyHelper("---end partial work---");

                    Object res = task.getResult();
                    LOG.log(Level.INFO, "Result = {0}", res);

                    LOG.info("Sending result to server...");
                    rc.sendResult(GridClient.getClientId(), res);
                }

                LOG.info("Server did not send parameter for work. Task assumed finished.");
            } catch (ClassNotFoundException | RemoteException e) {
                System.out.println(e.toString());
            }
        } else {

        }
    }

    public void startTask(String fullQualifiedTaskName) {
        try {
            taskClassLoader = new RMIClassLoader(rc, fullQualifiedTaskName);
            task = taskClassLoader.getTask();

            taskSolver = new Thread(this);
            taskSolver.start();

        } catch (RemoteException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public void abortTask() {
        if (task != null) {
            if (!taskSolver.getState().equals(State.TERMINATED)) {

                taskSolver.stop();

                int transmitTries = 0;
                while (transmitTries < 3) {
                    try {
                        rc.endTask(GridClient.getClientId());
                        break;
                    } catch (RemoteException exc) {
                        transmitTries++;
                    }
                }
            }
        }
    }

    private void logAndNotifyHelper(String msg) {
        LOG.info(msg);
        notifyObserver(msg);
    }

    public void notifyObserver(Object params) {
        ((Observer) observer).update(this, params);
    }

}
