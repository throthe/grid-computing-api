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
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.IRemoteConnection;

public class RMIClassLoader extends ClassLoader {

    protected final static Logger LOG = Logger.getLogger(RMIClassLoader.class.getName());

    private UserTask task;
    private String uniqueTaskName;
    private ClassLoader superLoader;

    private IRemoteConnection rc;

    public RMIClassLoader(IRemoteConnection rc, UserTask task) {
        this.rc = rc;
        this.task = task;
    }

    public RMIClassLoader(IRemoteConnection rc, String uniqueTaskName) throws RemoteException, ClassNotFoundException {
        this.rc = rc;
        this.uniqueTaskName = uniqueTaskName;

        superLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this);

        LOG.log(Level.INFO, "Try to load task <{0}>", uniqueTaskName);
        task = rc.loadTask(uniqueTaskName, GridClient.getClientId());
    }

    public UserTask getTask() {
        return this.task;
    }

    public void loadTaskClasses() throws ClassNotFoundException {
        if (task != null) {
            String[] userTaskClasses = task.getBackEnd().getUserTaskClasses();

            if (userTaskClasses != null) {
                for (String clsName : userTaskClasses) {
                    LOG.log(Level.INFO, "load and resolve class <{0}>", clsName);
                    resolveClass(loadClass(clsName));
                }
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String msg = "Client request class <" + name + ">";
        LOG.info(msg);

        byte[] classBytes = null;
        Class<?> cls = null;

        try {
            classBytes = rc.loadClass(this.uniqueTaskName, name);
        } catch (RemoteException e) {
            msg = "Remote Exception while finding class <" + name + ">!";
            LOG.log(Level.SEVERE, msg);
            LOG.log(Level.SEVERE, e.toString());
        }

        try {
            if (classBytes != null) {
                cls = defineClass(name, classBytes, 0, classBytes.length);
            }
            if (cls != null) {
                cls = superLoader.loadClass(name);
            }
        } catch (ClassNotFoundException e) {
            msg = "Finding class <" + name + "> failed!";
            LOG.log(Level.SEVERE, msg);
            LOG.log(Level.SEVERE, e.toString());
        }

        return cls;
    }

}
