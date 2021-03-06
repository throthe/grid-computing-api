package model;
// Generated Jan 9, 2018 3:15:18 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * ClassFiles generated by hbm2java
 */
public class ClassFiles  implements java.io.Serializable {


     private Integer id;
     private Short version;
     private String location;
     private Set<UserTaskClassFiles> userTaskClassFileses = new HashSet<UserTaskClassFiles>(0);
     private Set<UserTask> userTasks = new HashSet<UserTask>(0);
     private Set<TaskManagerClassFiles> taskManagerClassFileses = new HashSet<TaskManagerClassFiles>(0);
     private Set<TaskManager> taskManagers = new HashSet<TaskManager>(0);

    public ClassFiles() {
    }

    public ClassFiles(String location, Set<UserTaskClassFiles> userTaskClassFileses, Set<UserTask> userTasks, Set<TaskManagerClassFiles> taskManagerClassFileses, Set<TaskManager> taskManagers) {
       this.location = location;
       this.userTaskClassFileses = userTaskClassFileses;
       this.userTasks = userTasks;
       this.taskManagerClassFileses = taskManagerClassFileses;
       this.taskManagers = taskManagers;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Short getVersion() {
        return this.version;
    }
    
    public void setVersion(Short version) {
        this.version = version;
    }
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    public Set<UserTaskClassFiles> getUserTaskClassFileses() {
        return this.userTaskClassFileses;
    }
    
    public void setUserTaskClassFileses(Set<UserTaskClassFiles> userTaskClassFileses) {
        this.userTaskClassFileses = userTaskClassFileses;
    }
    public Set<UserTask> getUserTasks() {
        return this.userTasks;
    }
    
    public void setUserTasks(Set<UserTask> userTasks) {
        this.userTasks = userTasks;
    }
    public Set<TaskManagerClassFiles> getTaskManagerClassFileses() {
        return this.taskManagerClassFileses;
    }
    
    public void setTaskManagerClassFileses(Set<TaskManagerClassFiles> taskManagerClassFileses) {
        this.taskManagerClassFileses = taskManagerClassFileses;
    }
    public Set<TaskManager> getTaskManagers() {
        return this.taskManagers;
    }
    
    public void setTaskManagers(Set<TaskManager> taskManagers) {
        this.taskManagers = taskManagers;
    }




}


