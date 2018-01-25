package model;
// Generated Jan 9, 2018 3:15:18 PM by Hibernate Tools 4.3.1



/**
 * TaskDefinition generated by hbm2java
 */
public class TaskDefinition  implements java.io.Serializable {


     private Integer id;
     private TaskManager taskManager;
     private UserTask userTask;
     private String name;
     private String description;

    public TaskDefinition() {
    }

    public TaskDefinition(TaskManager taskManager, UserTask userTask, String name, String description) {
       this.taskManager = taskManager;
       this.userTask = userTask;
       this.name = name;
       this.description = description;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public TaskManager getTaskManager() {
        return this.taskManager;
    }
    
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    public UserTask getUserTask() {
        return this.userTask;
    }
    
    public void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }




}

