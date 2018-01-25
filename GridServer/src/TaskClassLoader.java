/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import interfaces.ITaskClassLoader;
import interfaces.ITaskDefinition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskClassLoader extends ClassLoader implements ITaskClassLoader {

    protected static final Logger LOG = Logger.getLogger(TaskClassLoader.class.getName());

    private final HashMap<String, File> classes;

    public TaskClassLoader(ClassLoader parent) {
        super(parent);
        this.classes = new HashMap<>();
    }

    @Override
    public Class<?> loadClass(String taskName) throws ClassNotFoundException {
        return super.loadClass(taskName);
    }

    @Override
    public String registerClass(File classFile) throws ClassNotFoundException {
        return this._registerClass(classFile);
    }

    @Override
    public byte[] loadClassData(String fullQualifiedClassName) throws ClassNotFoundException {
        return this._loadClassData(this.classes.get(fullQualifiedClassName));
    }

    @Override
    public void registerAllClasses(ITaskDefinition def) throws ClassNotFoundException {
        this._registerAllClasses(def);
    }

    public void _registerAllClasses(ITaskDefinition def) throws ClassNotFoundException {
        LOG.log(Level.INFO, "Register all classes called.");

        HashMap<String, String> classesLoaded = new HashMap<>();

        def.setUserTask(registerClass(new File(def.getUserTask())));
        String[] userTaskClasses = def.getUserTaskClasses();

        if (userTaskClasses != null) {
            String[] userTaskClassNames = new String[userTaskClasses.length];

            for (int i = 0; i < userTaskClasses.length; i++) {
                userTaskClassNames[i] = registerClass(new File(userTaskClasses[i]));
                classesLoaded.put(userTaskClasses[i], userTaskClassNames[i]);
            }

            def.setUserTaskClasses(userTaskClassNames);
        }

        def.setTaskManager(registerClass(new File(def.getTaskManager())));
        String[] taskManagerClasses = def.getTaskManagerClasses();

        if (taskManagerClasses != null) {
            String[] taskManagerClassNames = new String[taskManagerClasses.length];

            for (int i = 0; i < taskManagerClasses.length; i++) {
                if (!classesLoaded.containsKey(taskManagerClasses[i])) {
                    taskManagerClassNames[i] = registerClass(new File(taskManagerClasses[i]));
                } else {
                    taskManagerClassNames[i] = classesLoaded.get(taskManagerClasses[i]);
                }
            }

            def.setTaskManagerClasses(taskManagerClassNames);
        }
    }

    private String _registerClass(File classFile) throws ClassNotFoundException {
        LOG.log(Level.INFO, "Register full path class <{0}>", classFile.toString());

        byte[] classData = _loadClassData(classFile);
        LOG.log(Level.INFO, "class <{0}> [{1}bytes]", new Object[]{classFile.getName(), classData.length});

        String fullQualifiedClassName;

        Class<?> cls = defineClass(null, classData, 0, classData.length);
        fullQualifiedClassName = cls.getName();
        System.out.println(fullQualifiedClassName);

        this.classes.put(fullQualifiedClassName, classFile);
        return fullQualifiedClassName;
    }

    private byte[] _loadClassData(File classFile) throws ClassNotFoundException {
        if (classFile != null && classFile.exists()) {

            byte[] classData;
            RandomAccessFile classAccessFile = null;

            try {
                classAccessFile = new RandomAccessFile(classFile, "r");
                classData = new byte[(int) classAccessFile.length()];
                classAccessFile.readFully(classData);
            } catch (FileNotFoundException e) {
                throw new ClassNotFoundException("Class doesnt exist", e);
            } catch (IOException e) {
                System.err.println("#FAILURE: Class<" + classFile + "> -> IOException while reading.");
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

            LOG.log(Level.INFO, "#SUCCESS: Class <{0}> loaded", classFile);

            return classData;
        } else {

            throw new ClassNotFoundException("Class <" + classFile + "> doesn't exist");
        }
    }
}
