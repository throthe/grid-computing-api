/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServerClassLoader extends ClassLoader {

    protected final static Logger LOG = Logger.getLogger(ServerClassLoader.class.getName());

    private List<TaskClassLoader> childClassLoaders;

    public ServerClassLoader() {
        super();

        childClassLoaders = new ArrayList<TaskClassLoader>();
    }

    public void addTaskClassLoader(TaskClassLoader tcl) {
        this.childClassLoaders.add(tcl);
    }

    @Override
    public Class<?> findClass(String fullQualifiedClassName) throws ClassNotFoundException {
        LOG.info("Server:TaskClassLoader.findClass(String fullQualifiedClassName");
        LOG.info("{");
        LOG.info("	fullQualifiedClassName = " + fullQualifiedClassName);

        Class<?> cls = null;

        for (TaskClassLoader tcl : this.childClassLoaders) {
            if ((cls = tcl.loadClass(fullQualifiedClassName)) != null) {
                LOG.info("foundClass = " + cls.getName());
                LOG.info("}");
                return cls;
            }
        }

        return null;
    }
}
