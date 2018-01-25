/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import interfaces.ITaskDefinition;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import xml.XStreamUtils;

public class TaskDefinition implements ITaskDefinition {

    private String uniqueTaskName;
    private String taskManager;
    private String userTask;
    private String description;

    private String[] taskManagerClasses;
    private String[] userTaskClasses;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTaskManager() {
        return taskManager;
    }

    @Override
    public void setTaskManager(String taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public String[] getTaskManagerClasses() {
        return taskManagerClasses;
    }

    @Override
    public void setTaskManagerClasses(String[] taskManagerClasses) {
        this.taskManagerClasses = taskManagerClasses;
    }

    @Override
    public String getUserTask() {
        return userTask;
    }

    @Override
    public void setUserTask(String userTask) {
        this.userTask = userTask;
    }

    @Override
    public String[] getUserTaskClasses() {
        return userTaskClasses;
    }

    @Override
    public void setUserTaskClasses(String[] userTaskClasses) {
        this.userTaskClasses = userTaskClasses;
    }

    @Override
    public String getUniqueTaskName() {
        return uniqueTaskName;
    }

    @Override
    public void setUniqueTaskName(String uniqueTaskName) {
        this.uniqueTaskName = uniqueTaskName;
    }

    public static void save(TaskDefinition def, File dest) throws IOException {
        XStreamUtils.saveAsXml(def, dest);
    }

    public static TaskDefinition load(File src) throws IOException {
        return (TaskDefinition) XStreamUtils.loadFromXml(src);
    }

    @Override
    public String toString() {
        String msg = "{ " + "\n"
                + "name: " + this.uniqueTaskName + "\n"
                + "desc: " + this.description + "\n"
                + "user-task: " + this.userTask + "\n"
                + "user-task-classes: " + Arrays.toString(this.userTaskClasses) + "\n"
                + "task-manager: " + this.taskManager + "\n"
                + "task-manager-classes: " + Arrays.toString(this.taskManagerClasses) + "\n"
                + "}";
        return msg;
    }

    //TODO: set javaversion
    //private JavaVersion javaVersion;
}
