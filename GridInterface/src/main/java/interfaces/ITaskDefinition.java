/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author thomas_r
 */
public interface ITaskDefinition {

    public String getDescription();

    public void setDescription(String description);

    public String getTaskManager();

    public void setTaskManager(String taskManager);

    public String[] getTaskManagerClasses();

    public void setTaskManagerClasses(String[] taskManagerClasses);

    public String getUserTask();

    public void setUserTask(String userTask);

    public String[] getUserTaskClasses();

    public void setUserTaskClasses(String[] userTaskClasses);

    public String getUniqueTaskName();

    public void setUniqueTaskName(String uniqueTaskName);
}
