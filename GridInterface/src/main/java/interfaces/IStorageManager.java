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
import java.io.IOException;

import exception.IdentifierNotFoundException;
import exception.NotUniqueIdentifierException;

public interface IStorageManager {

    public abstract void store(String key, Object result) throws IOException, NotUniqueIdentifierException;

    public abstract String store(Object result) throws IOException;

    public abstract void enforceStore(String key, Object result) throws IOException;

    public abstract Object load(String key) throws IdentifierNotFoundException, IOException;

}
