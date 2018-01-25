/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import interfaces.IRemoteConnection;
import util.JavaVersion;
import util.UserTask;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RemoteConnectionImpl extends UnicastRemoteObject implements IRemoteConnection {

    private static final long serialVersionUID = 5433052561614095241L;

    private final GridServer server;

    public RemoteConnectionImpl(GridServer server) throws RemoteException {
        super();
        this.server = server;
    }

    @Override
    public String getClientId() throws RemoteException {
        return this.server.generateNewClientID();
    }

    @Override
    public String[] getTaskList() throws RemoteException {
        return this.server.getTasks();
    }

    @Override
    public String getTaskDescription(String uniqueTaskName) throws RemoteException {
        return this.server.getTaskDescription(uniqueTaskName);
    }

    @Override
    public Boolean isTaskAcceptStatusQuery(String uniqueTaskName) throws RemoteException {
        return this.server.isTaskAcceptStatusQuery(uniqueTaskName);
    }

    @Override
    public Integer getTaskProgress(String uniqueTaskName) throws RemoteException {
        return this.server.getTaskProgress(uniqueTaskName);
    }

    @Override
    public Integer getNumberOfVolunteers(String uniqueTaskName) throws RemoteException {
        return this.server.getNumberOfVolunteers(uniqueTaskName);
    }

    @Override
    public JavaVersion getJavaVersion(String uniqueTaskName) throws RemoteException {
        return JavaVersion.VERSION_1_8;
    }

    @Override
    public UserTask loadTask(String uniqueTaskName, String clientId) throws ClassNotFoundException, RemoteException {
        return this.server.loadTask(uniqueTaskName, clientId);
    }

    @Override
    public byte[] loadClass(String uniqueTaskName, String fullQualifiedClassName)
            throws ClassNotFoundException, RemoteException {
        return this.server.loadClass(uniqueTaskName, fullQualifiedClassName);
    }

    @Override
    public HashMap<String, Object> loadParams(String clientId) throws RemoteException {
        return this.server.loadParams(clientId);
    }

    @Override
    public void sendResult(String clientId, Object res) throws RemoteException {
        this.server.handOverResult(clientId, res);
    }

    @Override
    public boolean isParamFinished(String clientId) throws RemoteException {
        return this.server.isParamFinished(clientId);
    }

    @Override
    public boolean isTaskFinished(String uniqueTaskName) throws RemoteException {
        return this.server.isTaskFinished(uniqueTaskName);
    }

    @Override
    public void endTask(String clientId) throws RemoteException {
        this.server.endTask(clientId);
    }

    @Override
    public void shutDownClient(String clientId) throws RemoteException {
        this.server.removeClient(clientId);
    }
}
