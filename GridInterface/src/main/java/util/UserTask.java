package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
import interfaces.ITaskStatusListener;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UserTask implements Serializable, Runnable {

    private static final long serialVersionUID = 3097185146255503657L;
    protected static final Logger LOG = Logger.getLogger(UserTask.class.getName());

    boolean firstCall = true;
    private UserTaskBackEnd backEnd;

    public UserTask() {
        super();
    }

    public final void injectBackEnd(UserTaskBackEnd backEnd) {
        if (firstCall) {
            this.backEnd = backEnd;
            firstCall = false;
        }
    }

    public final UserTaskBackEnd getBackEnd() {
        Class<?> cls = sun.reflect.Reflection.getCallerClass(0);
        LOG.log(Level.INFO, "UserTask Backend <{0}> in package <{1}>", new Object[]{cls.toString(), cls.getPackage().getName()});
        return this.backEnd;
    }

    public final String getUniqueTaskName() {
        return this.backEnd.getUniqueTaskName();
    }

    public final Object getParameterByName(String paramName) {
        return this.backEnd.getParameterByName(paramName);
    }

    protected final ITaskStatusListener getObserver() {
        return this.backEnd.getStatusObserver();
    }

    @Override
    public abstract void run();

    public abstract Object getResult();

    public abstract boolean isProgressBarRequired();

    public abstract boolean isTextOutputAreaRequired();

    public abstract int getMaxProgressValue();
}
