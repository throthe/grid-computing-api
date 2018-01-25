/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas_r
 */
public class TaskClassLoader extends ClassLoader {

    protected static final Logger LOG = Logger.getLogger(TaskClassLoader.class.getName());

    public TaskClassLoader(ClassLoader parent) {
        super(parent);
    }

    public String getFullQualifiedClassName(File file) throws ClassNotFoundException {

        byte[] classData = getClassDataFromFile(file);
        LOG.log(Level.INFO, "bytes: {0}", new Object[]{classData.length});
        Class<?> cls = null;
        
        if (classData.length > 0) {
            cls = defineClass(null, classData, 0, classData.length);
            LOG.log(Level.INFO, "class <{0}> [{1}bytes]", new Object[]{cls.getName(), classData.length});
        }

        return cls != null ? cls.getName() : null;
    }

    private byte[] getClassDataFromFile(File file) throws ClassNotFoundException {
       
        if (file != null && file.exists()) {

            byte[] classData;
            RandomAccessFile classAccessFile = null;

            try {

                classAccessFile = new RandomAccessFile(file, "r");
                classData = new byte[(int) classAccessFile.length()];
                classAccessFile.readFully(classData);

            } catch (FileNotFoundException e) {
                throw new ClassNotFoundException("Class doesnt exsist!", e);
            } catch (IOException e) {
                System.err.println("#FAILURE: Class<" + file + "> -> IOException while reading.");
                throw new ClassNotFoundException("Access not possible", e);
            } finally {
                if (classAccessFile != null) {
                    try {
                        classAccessFile.close();
                    } catch (IOException e) {
                        // TODO
                    }
                }
            }
            return classData;

        } else {
            throw new ClassNotFoundException("Class <" + file + "> doesn't exist");
        }
    }
}
