package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import util.JavaVersion;
import util.UserTask;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
public interface IRemoteConnection extends Remote {

    public String getClientId() throws RemoteException;

    public String[] getTaskList() throws RemoteException;

    public String getTaskDescription(String uniqueTaskName) throws RemoteException;

    public Boolean isTaskAcceptStatusQuery(String uniqueTaskName) throws RemoteException;

    public Integer getTaskProgress(String uniqueTaskName) throws RemoteException;

    public Integer getNumberOfVolunteers(String uniqueTaskName) throws RemoteException;

    public JavaVersion getJavaVersion(String uniqueTaskName) throws RemoteException;

    public UserTask loadTask(String uniqueTaskName, String clientId) throws ClassNotFoundException, RemoteException;

    public byte[] loadClass(String uniqueTaskName, String fullQualifiedClassName) throws ClassNotFoundException, RemoteException;

    public HashMap<String, Object> loadParams(String clientId) throws RemoteException;

    public void sendResult(String clientId, Object res) throws RemoteException;

    public boolean isParamFinished(String clientId) throws RemoteException;

    public boolean isTaskFinished(String uniqueTaskName) throws RemoteException;

    public void endTask(String clientId) throws RemoteException;

    public void shutDownClient(String clientId) throws RemoteException;
}
