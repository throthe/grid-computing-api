package manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IParameterManager;

public class ParameterManager implements IParameterManager {

    protected final static Logger LOG = Logger.getLogger(ParameterManager.class.getName());

    private BaseTaskManager taskManager;
    private Long paramSequence;

    private final HashMap<String, Long> workingClientParams;
    private final HashMap<Long, List<String>> workingParamClients;
    private final HashMap<Long, HashMap<String, Object>> params;

    private final List<Long> todoParams;
    private final List<Long> doneParams;

    private boolean taskFinished;

    public ParameterManager(BaseTaskManager taskManager) {
        this.taskManager = taskManager;
        this.paramSequence = 0L;

        this.workingClientParams = new HashMap<>();
        this.workingParamClients = new HashMap<>();
        this.params = new HashMap<>();

        this.todoParams = new ArrayList<>();
        this.doneParams = new ArrayList<>();

        this.taskFinished = false;
    }

    private void newParam(String clientId, HashMap<String, Object> params) {
        Long paramId = this.paramSequence++;
        this.params.put(paramId, params);
        registerClient(clientId, paramId);
    }

    @Override
    public HashMap<String, Object> paramSolved(String clientId) {
        LOG.info("start paramSolved method:");
        LOG.log(Level.INFO, "clientID to remove: {0}", clientId);
        Long paramId = this.workingClientParams.remove(clientId);
        LOG.log(Level.INFO, "removed paramID: {0}", paramId);

        if (paramId != null) {
            boolean add = this.doneParams.add(paramId);
            for (String obsoleteClientId : this.workingParamClients.remove(paramId)) {
                this.workingClientParams.remove(obsoleteClientId);
            }
            return this.params.get(paramId);
        }
        return null;
    }

    @Override
    public boolean isParamFinished(String clientId) {
        return this.workingClientParams.get(clientId) == null
                || this.doneParams.contains(this.workingClientParams.get(clientId));
    }

    @Override
    public HashMap<String, Object> receiveParam(String clientId) {
        if (!this.todoParams.isEmpty()) {
            return registerClient(clientId, this.todoParams.remove(0));
        }

        HashMap<String, Object> paramsNext;

        paramsNext = this.taskManager.getNextParams();
        if (paramsNext != null) {
            newParam(clientId, paramsNext);
            return paramsNext;
        }

        paramsNext = receiveDuplicateParam(clientId);
        if (paramsNext != null) {
            return paramsNext;
        }

        this.taskFinished = true;
        return null;
    }

    private HashMap<String, Object> receiveDuplicateParam(String clientId) {
        Long lowestParamId = null;
        int sizeCounter = -1;

        for (Long key : this.workingParamClients.keySet()) {
            int size = this.workingParamClients.get(key).size();

            if (size == 1) {
                return registerClient(clientId, key);
            } else if (sizeCounter == -1 || size < sizeCounter) {
                sizeCounter = size;
                lowestParamId = key;
            }
        }

        if (lowestParamId == null) {
            return null;
        } else {
            return registerClient(clientId, lowestParamId);
        }
    }

    private HashMap<String, Object> registerClient(String clientId, Long paramId) {
        this.workingClientParams.put(clientId, paramId);

        List<String> workingClients = this.workingParamClients.get(paramId);

        if (workingClients == null) {
            List<String> clientIdList = new ArrayList<>();
            clientIdList.add(clientId);
            this.workingParamClients.put(paramId, clientIdList);
        } else {
            workingClients.add(clientId);
        }

        return this.params.get(paramId);
    }

    @Override
    public void abortClient(String clientId) {
        Long paramId = this.workingClientParams.remove(clientId);

        if (this.workingParamClients.get(paramId).size() == 1) {
            this.workingParamClients.remove(paramId);
            this.todoParams.add(paramId);
        } else {
            this.workingParamClients.get(paramId).remove(clientId);
        }
    }

    @Override
    public boolean isTaskFinished() {
        return taskFinished;
    }

    @Override
    public void cleanup() {
        this.taskManager = null;
    }

    @Override
    public int getNumberOfWorkingClients() {
        return this.workingClientParams.size();
    }
}
