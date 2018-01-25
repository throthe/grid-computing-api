/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import model.ClassFiles;
import model.TaskDefinition;
import model.TaskManager;
import model.UserTask;

/**
 *
 * @author thomas_r
 */
public class HibernateHelper {

    public TaskDefinition create() {

        ClassFiles file1 = new ClassFiles();
        ClassFiles file2 = new ClassFiles();
        file1.setLocation("C:\\Users\\thomas_r\\Documents\\NetBeansProjects\\GridServer\\task\\prim\\PrimCheckTask.class");
        file2.setLocation("C:\\Users\\thomas_r\\Documents\\NetBeansProjects\\GridServer\\task\\prim\\PrimCheckTaskManager.class");

        UserTask task = new UserTask();
        task.setName("TestTask1Task");
        task.setClassFiles(file1);

        TaskManager manager = new TaskManager();
        manager.setName("TeskTask1Manager");
        manager.setClassFiles(file2);

        TaskDefinition def = new TaskDefinition();
        def.setName("TestTask1");
        def.setDescription("This is a Test!");
        def.setTaskManager(manager);
        def.setUserTask(task);

        return def;
    }
}
