package model;
// Generated Jan 9, 2018 3:15:18 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;


/**
 * TaskManager generated by hbm2java
 */
public class TaskManager  implements java.io.Serializable {


     private Integer id;
     private ClassFiles classFiles;
     private String name;
     private Set<TaskDefinition> taskDefinitions = new HashSet<>(0);
     private Set<TaskManagerClassFiles> taskManagerClassFileses = new HashSet<>(0);

    public TaskManager() {
    }

    public TaskManager(ClassFiles classFiles, String name, Set<TaskDefinition> taskDefinitions, Set<TaskManagerClassFiles> taskManagerClassFileses) {
       this.classFiles = classFiles;
       this.name = name;
       this.taskDefinitions = taskDefinitions;
       this.taskManagerClassFileses = taskManagerClassFileses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public ClassFiles getClassFiles() {
        return this.classFiles;
    }
    
    public void setClassFiles(ClassFiles classFiles) {
        this.classFiles = classFiles;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set<TaskDefinition> getTaskDefinitions() {
        return this.taskDefinitions;
    }
    
    public void setTaskDefinitions(Set<TaskDefinition> taskDefinitions) {
        this.taskDefinitions = taskDefinitions;
    }
    public Set<TaskManagerClassFiles> getTaskManagerClassFileses() {
        return this.taskManagerClassFileses;
    }
    
    public void setTaskManagerClassFileses(Set<TaskManagerClassFiles> taskManagerClassFileses) {
        this.taskManagerClassFileses = taskManagerClassFileses;
    }




}


