/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import interfaces.IStorageManager;
import interfaces.IParameterManager;
import interfaces.ITaskDefinition;
import interfaces.ITaskManager;
import interfaces.ITaskClassLoader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas_r
 */
public abstract class BaseTaskManager implements ITaskManager {

    protected static Logger logger = Logger.getLogger(BaseTaskManager.class.getName());

    private IStorageManager storageManager;
    private IParameterManager parameterManager;
    private ITaskDefinition taskDefinition;

    private ITaskClassLoader taskClassLoader;

    protected final IStorageManager getStorageManager() {
        return this.storageManager;
    }

    public final void injectStorageManager(IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public BaseTaskManager() {
        this.parameterManager = new ParameterManager(this);
    }

    public final ITaskDefinition getTaskDefinition() {
        return this.taskDefinition;
    }

    public final void injectTaskDefinition(ITaskDefinition def) {
        this.taskDefinition = def;
    }

    public final ITaskClassLoader getTaskClassLoader() {
        return this.taskClassLoader;
    }

    public final void injectTaskClassLoader(ITaskClassLoader taskClassLoader) {
        this.taskClassLoader = taskClassLoader;
    }

    public final HashMap<String, Object> handOutParams(String clientId) {
        return this.parameterManager.receiveParam(clientId);
    }

    public final void abortClient(String clientId) {
        this.parameterManager.abortClient(clientId);
    }

    public final boolean isParamFinished(String clientId) {
        return this.parameterManager.isParamFinished(clientId);
    }

    public final boolean isTaskFinished() {
        return this.parameterManager.isTaskFinished();
    }

    public final void cleanup() {
        this.parameterManager.cleanup();
        this.parameterManager = null;
        this.storageManager = null;
        this.taskDefinition = null;
        this.taskClassLoader = null;

        logger.log(Level.INFO, "{0} was cleaned up!", this.taskDefinition.getUniqueTaskName());
    }

    public final void forceTaskToFinish() {
        //TODO implement force task to finish
        logger.log(Level.WARNING, "{0} was forced to finish!", this.taskDefinition.getUniqueTaskName());
    }

    public final void finishedResult(String clientId, Object res) {
        HashMap<String, Object> solvedParams = this.parameterManager.paramSolved(clientId);

        if (solvedParams != null) {
            finishedResult(solvedParams, res);
        }
    }

    public final int getNumberOfWorkingClients() {
        return this.parameterManager.getNumberOfWorkingClients();
    }

    @Override
    public abstract HashMap<String, Object> getNextParams();

    @Override
    public abstract void finishedResult(HashMap<String, Object> params, Object res);

    @Override
    public abstract boolean isStatusQueryAccepted();

    @Override
    public abstract int getTaskProgress();
}
