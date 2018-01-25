/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.File;

/**
 *
 * @author thomas_r
 */
public interface ITaskClassLoader {

    public byte[] loadClassData(String fullQualifiedClassName) throws ClassNotFoundException;

    public void registerAllClasses(ITaskDefinition def) throws ClassNotFoundException;
    
    public String registerClass(File classFile) throws ClassNotFoundException;
    
    public Class<?> loadClass(String taskName) throws ClassNotFoundException;
}
