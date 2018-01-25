/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
public class ClientInformation {

    private ClientStatus status;
    private String currentWorkingTask;

    public ClientInformation() {
        this.status = ClientStatus.FREE;
        this.currentWorkingTask = null;
    }

    public ClientStatus getStatus() {
        return this.status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
        if (status == ClientStatus.FREE) {
            this.currentWorkingTask = null;
        }
    }

    public String getCurrentWorkingTask() {
        return this.currentWorkingTask;
    }

    public void setCurrentWorkingTask(String currentWorkingTask) {
        this.currentWorkingTask = currentWorkingTask;
    }

}
