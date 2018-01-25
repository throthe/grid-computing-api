/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.TaskClassLoader;
import model.ClassFiles;

import model.TaskDefinition;
import model.TaskManager;
import model.UserTask;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author thomas_r
 */
public class TestMain {

    private static SessionFactory factory;
    private static ServiceRegistry registry;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String m = "";
        //System.out.println("Enter a message:");
        //m = in.nextLine();

        try {
            Configuration conf = new Configuration().configure();
            registry = new StandardServiceRegistryBuilder().applySettings(
                    conf.getProperties()).build();
            factory = conf.buildSessionFactory(registry);
        } catch (HibernateException e) {
            System.err.println("Failed to create session factory object" + e);
            throw new ExceptionInInitializerError(e);
        }

        Session session = factory.openSession();
        Transaction tx = null;
        Integer testID = null;

        try {
            tx = session.beginTransaction();
            HibernateHelper helper = new HibernateHelper();
            TaskDefinition msg = new TaskDefinition();

            ClassFiles file1 = new ClassFiles();
            ClassFiles file2 = new ClassFiles();
            file1.setLocation("C:\\Users\\thomas_r\\Documents\\NetBeansProjects\\GridServer\\task\\prim\\PrimCheckTask.class");
            file2.setLocation("C:\\Users\\thomas_r\\Documents\\NetBeansProjects\\GridServer\\task\\prim\\PrimCheckTaskManager.class");

            ClassLoader parent = TestMain.class.getClassLoader();
            TaskClassLoader loader = new TaskClassLoader(parent);

            try {
                String s1 = loader.getFullQualifiedClassName(new File(file1.getLocation()));
                System.out.println("full class name: " + s1);
                String s2 = loader.getFullQualifiedClassName(new File(file2.getLocation()));
                System.out.println("full class name: " + s2);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
            }

            //testID = (Integer)session.save(file1);
            //testID = (Integer)session.save(file2);
            //UserTask task = new UserTask();
            //task.setName("TestTask1Task");
            //task.setClassFiles(file1);
            //testID = (Integer)session.save(task);
            //TaskManager manager = new TaskManager();
            //manager.setName("TeskTask1Manager");
            //manager.setClassFiles(file2);
            //testID = (Integer)session.save(manager);
            //msg.setName("TestTask2");
            //msg.setDescription("This is a Test!");
            //msg.setTaskManager(manager);
            //msg.setUserTask(task);
            //testID = (Integer) session.save(msg);
            List messages = session.createQuery("FROM TaskDefinition").list();

            for (Iterator it = messages.iterator(); it.hasNext();) {

                TaskDefinition td = (TaskDefinition) it.next();
                System.out.println("message: " + td.getName());
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        StandardServiceRegistryBuilder.destroy(registry);
    }

}
