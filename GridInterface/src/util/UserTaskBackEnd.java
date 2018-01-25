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
import java.util.HashMap;

public class UserTaskBackEnd implements Serializable {

    private static final long serialVersionUID = 3097185127255503657L;

    private String[] userTaskClasses;
    private HashMap<String, Object> parameters;
    private String uniqueTaskName;
    private ITaskStatusListener statusObserver;

    public UserTaskBackEnd() {
        super();
    }

    public final void setUniqueTaskName(String uniqueTaskName) {
        this.uniqueTaskName = uniqueTaskName;
    }

    public final String getUniqueTaskName() {
        return this.uniqueTaskName;
    }

    public final void setUserTaskClasses(String[] userTaskClasses) {
        this.userTaskClasses = userTaskClasses;
    }

    public final String[] getUserTaskClasses() {
        return this.userTaskClasses;
    }

    public final void injectParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public final Object getParameterByName(String paramName) {
        return this.parameters.get(paramName);
    }

    public final void setStatusObserver(ITaskStatusListener statusObserver) {
        this.statusObserver = statusObserver;
    }

    protected final ITaskStatusListener getStatusObserver() {
        return this.statusObserver;
    }
}
