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
import util.UserTaskBackEnd;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.IStorageManager;
import interfaces.ITaskClassLoader;
import manager.BaseTaskManager;

import storage.StorageManagerImpl;

@SuppressWarnings("deprecation")
public class GridServer extends Observable {

    protected static final Logger LOG = Logger.getLogger(GridServer.class.getName());

    private final ServerClassLoader serverClassLoader;
    private final HashMap<String, BaseTaskManager> tasks;
    private final HashMap<String, ClientInformation> clients;
    //private Observer ui;

    public GridServer() {
        LOG.info("GridServer ctor called.");
        this.tasks = new HashMap<>();
        this.clients = new HashMap<>();

        this.serverClassLoader = new ServerClassLoader();
        Thread.currentThread().setContextClassLoader(serverClassLoader);
    }

    public void generateNewTaskManager(TaskDefinition def) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        LOG.log(Level.INFO, "Generate new Task Manager called.");

        ClassLoader parent = TaskClassLoader.class.getClassLoader();
        TaskClassLoader taskClassLoader = new TaskClassLoader(parent);

        // TODO hack for loading UserTask
        // String urlUserTask =
        // "C:/Env/workspace_github/server/target/classes/thr/grid/server/util/UserTask.class";
        // String urlTaskManager =
        // "C:/Env/workspace_github/server/target/classes/thr/grid/server/TaskManager.class";
        // String urlNotUniqueIdentifierEx =
        // "C:/Env/workspace_github/server/target/classes/thr/grid/server/exception/NotUniqueIdentifierException.class";
        // String urlParamManager =
        // "C:/Env/workspace_github/server/target/classes/thr/grid/server/ParameterManager.class";
        // taskClassLoader.loadClassTest("thr.grid.server.util.UserTask", urlUserTask);
        // taskClassLoader.loadClassTest("thr.grid.server.TaskManager", urlTaskManager);
        // taskClassLoader.loadClassTest("thr.grid.server.exception.NotUniqueIdentifierException",
        // urlNotUniqueIdentifierEx);
        // taskClassLoader.loadClassTest("thr.grid.server.ParameterManager",
        // urlParamManager);
        // TODO hack for loading UserTask
        // TODO here goes the normal flow...
        
        taskClassLoader.registerAllClasses(def);

        Class<?> taskManagerClass = taskClassLoader.loadClass(def.getTaskManager());
        Constructor<?> ctor = taskManagerClass.getDeclaredConstructors()[0];

        LOG.log(Level.INFO, "loaded task manager: {0}", taskManagerClass.getName());

        if (ctor == null) {
            throw new InstantiationException("Could not find default ctor!");
        }

        IStorageManager storageManager = (IStorageManager) new StorageManagerImpl(def.getUniqueTaskName());

        BaseTaskManager taskManager = (BaseTaskManager) ctor.newInstance();
        taskManager.injectTaskDefinition(def);
        taskManager.injectStorageManager((IStorageManager) storageManager);
        taskManager.injectTaskClassLoader((ITaskClassLoader) taskClassLoader);

        this.serverClassLoader.addTaskClassLoader(taskClassLoader);
        this.tasks.put(def.getUniqueTaskName(), taskManager);
    }

    public byte[] loadClass(String uniqueTaskName, String fullQualifiedClassName) throws ClassNotFoundException {
        LOG.log(Level.INFO, "Server - start loadClass ({0}): {1}", new Object[]{uniqueTaskName, fullQualifiedClassName});
        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);

        if (taskManager != null) {
            try {
                ITaskClassLoader classLoader;
                classLoader = (ITaskClassLoader)taskManager.getTaskClassLoader();
                return classLoader.loadClassData(fullQualifiedClassName);
            } catch (ClassNotFoundException e) {
                LOG.log(Level.SEVERE, "Class <{0}> not found!", fullQualifiedClassName);
            }
            LOG.log(Level.WARNING, "Server: Class {0}for Task {1}not found", new Object[]{fullQualifiedClassName, uniqueTaskName});
        }
        return null;
    }

    public String getFullQualifiedClassName(String uniqueTaskName) throws ClassNotFoundException {

        LOG.log(Level.INFO, "[Server] getFullQualifiedClassName({0})", uniqueTaskName);
        LOG.info("{");

        String name = null;
        BaseTaskManager tm = this.tasks.get(uniqueTaskName);

        if (tm != null) {
            String TaskName = tm.getTaskDefinition().getUserTask();

            LOG.log(Level.INFO, "\tTaskname = {0};", TaskName);
            Class<?> cls = tm.getTaskClassLoader().loadClass(TaskName);
            LOG.log(Level.INFO, "\tClass ={0}", cls);
            LOG.info("}");

            name = cls.getName();
        }
        return name;
    }

    protected String generateNewClientID() {
        LOG.log(Level.FINEST, "[Server] generateNewClientID()");

        String uuid;

        do {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
        } while (this.clients.keySet().contains(uuid));

        this.clients.put(uuid, new ClientInformation());

        return uuid;
    }

    public String[] getTasks() {
        return tasks.keySet().toArray(new String[]{});
    }

    protected String getTaskDescription(String uniqueTaskName) {
        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);
        if (taskManager != null) {
            return taskManager.getTaskDefinition().getDescription();
        }
        return null;
    }

    protected Boolean isTaskAcceptStatusQuery(String uniqueTaskName) {
        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);

        if (taskManager != null) {
            return taskManager.isStatusQueryAccepted();
        }

        return null;
    }

    public Integer getTaskProgress(String uniqueTaskName) {
        LOG.log(Level.FINEST, "[Server] getTaskProgress({0})", uniqueTaskName);

        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);
        Integer progress = null;

        if (taskManager != null) {
            if (taskManager.isStatusQueryAccepted()) {
                progress = taskManager.getTaskProgress();
            }
        }

        return progress;
    }

    public Integer getNumberOfVolunteers(String uniqueTaskName) {
        LOG.log(Level.FINEST, "[Server] getNumberOfVolunteers({0})", uniqueTaskName);

        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);
        Integer volunteers = null;

        if (taskManager != null) {
            if (taskManager.isStatusQueryAccepted()) {
                volunteers = taskManager.getNumberOfWorkingClients();
            }
        }

        return volunteers;
    }

    public UserTask loadTask(String uniqueTaskName, String clientId) throws ClassNotFoundException {
        LOG.log(Level.FINEST, "[Server] loadTask({0}, {1})", new Object[]{uniqueTaskName, clientId});

        this.clients.get(clientId).setCurrentWorkingTask(uniqueTaskName);

        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);
        UserTask task = null;

        if (taskManager != null) {

            String taskName = taskManager.getTaskDefinition().getUserTask();
            Class<?> cls = taskManager.getTaskClassLoader().loadClass(taskName);
            LOG.log(Level.FINEST, "Task class <{0}> loaded", cls.toString());

            try {

                task = (UserTask) cls.newInstance();
                LOG.log(Level.FINEST, task.toString());

                UserTaskBackEnd backend = new UserTaskBackEnd();
                backend.setUniqueTaskName(uniqueTaskName);
                backend.setUserTaskClasses(taskManager.getTaskDefinition().getUserTaskClasses());

                task.injectBackEnd(backend);

            } catch (IllegalAccessException | InstantiationException e) {
                LOG.log(Level.SEVERE, e.getMessage());
                task = null;
            }
        }

        return task;
    }

    protected HashMap<String, Object> loadParams(String clientId) {
        LOG.log(Level.INFO, "Request for new parmas for client <{0}>.", clientId);
        ClientInformation clientInformation = this.clients.get(clientId);

        if (clientInformation != null) {
            LOG.log(Level.INFO, "Client is: {0}", clientInformation);
            LOG.log(Level.INFO, "Client works on: {0}", clientInformation.getCurrentWorkingTask());

            BaseTaskManager taskManager = this.tasks.get(clientInformation.getCurrentWorkingTask());

            if (taskManager != null) {
                HashMap<String, Object> params = taskManager.handOutParams(clientId);

                clientInformation.setCurrentWorkingTask(taskManager.getTaskDefinition().getUniqueTaskName());
                clientInformation.setStatus(ClientStatus.WORKING);
                return params;
            }
        }
        
        LOG.log(Level.WARNING, "Params is null!");
        return null;
    }

    protected void handOverResult(String clientId, Object res) {
        ClientInformation clientInformation = this.clients.get(clientId);
        if (clientInformation != null) {
            BaseTaskManager taskManager = this.tasks.get(clientInformation.getCurrentWorkingTask());

            if (taskManager != null) {
                if (taskManager.isStatusQueryAccepted()) {
                    taskManager.finishedResult(clientId, res);
                }
            }
        }
    }

    protected boolean isParamFinished(String clientId) {
        String uniqueTaskName = this.clients.get(clientId).getCurrentWorkingTask();
        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);

        if (taskManager != null) {
            return taskManager.isParamFinished(clientId);
        }
        return true;
    }

    public boolean isTaskFinished(String uniqueTaskName) {
        BaseTaskManager taskManager = this.tasks.get(uniqueTaskName);

        if (taskManager != null) {
            return taskManager.isTaskFinished();
        }
        return true;
    }

    protected void endTask(String clientId) {
        ClientInformation clientInformation = this.clients.get(clientId);
        if (clientInformation != null) {
            BaseTaskManager taskManager = this.tasks.get(clientInformation.getCurrentWorkingTask());
            if (taskManager != null) {
                taskManager.abortClient(clientId);
                clientInformation.setStatus(ClientStatus.FREE);
            }
        }
    }

    protected void removeClient(String clientId) {
        ClientInformation clientInformation = this.clients.get(clientId);
        if (clientInformation.getStatus() == ClientStatus.WORKING) {
            BaseTaskManager taskManager = this.tasks.get(clientInformation.getCurrentWorkingTask());
            if (taskManager != null) {
                taskManager.abortClient(clientId);
            }
        }
        this.clients.remove(clientId);
    }

    public void clearTask(String uniqueTaskName) {
        BaseTaskManager taskManager = this.tasks.remove(uniqueTaskName);
        if (taskManager != null) {
            taskManager.cleanup();
        }
    }

    public void forceTaskToFinish(String uniqueTaskName) {
        BaseTaskManager taskManager = this.tasks.remove(uniqueTaskName);
        if (taskManager != null) {
            taskManager.forceTaskToFinish();
        }
    }

    @Deprecated
    public void setGUIMainform(Observer mainform) {
       // ui = mainform;
    }

    @Deprecated
    public void injectUI() {
        // MainForm mf = new MainForm(gs);
        // this.setGUIMainform(mf);
        // mf.setVisible(true);
    }
}
