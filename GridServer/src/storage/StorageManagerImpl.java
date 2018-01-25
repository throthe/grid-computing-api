/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

/**
 *
 * @author thomas_r
 */
import interfaces.IStorageManager;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import exception.IdentifierNotFoundException;
import exception.NotUniqueIdentifierException;
import xml.XStreamUtils;

public class StorageManagerImpl implements IStorageManager {

    private static final String STORAGE_FOLDER = "storage\\";

    private final String uniqueTaskName;

    public StorageManagerImpl(String uniqueTaskName) throws IOException {
        this.uniqueTaskName = uniqueTaskName;

        File projectFolder = new File(STORAGE_FOLDER);
        if (!projectFolder.exists()) {
            if (!projectFolder.mkdir()) {
                throw new IOException("Project folder <" + projectFolder + "> could not be created!");
            }
        }

        projectFolder = new File(STORAGE_FOLDER + uniqueTaskName);
        if (!projectFolder.exists()) {
            if (!projectFolder.mkdir()) {
                throw new IOException("Project folder <" + projectFolder + "> could not be created!");
            }
        }
    }

    @Override
    public void store(String key, Object res) throws IOException, NotUniqueIdentifierException {
        File file = new File(STORAGE_FOLDER + this.uniqueTaskName + "\\" + key);
        if (file.exists()) {
            throw new NotUniqueIdentifierException("key " + key + " already exists!");
        }
        XStreamUtils.saveAsXml(res, file);
    }

    @Override
    public String store(Object res) throws IOException {
        String key;
        File file;

        do {
            key = UUID.randomUUID().toString().replaceAll("-", "");
            file = new File(STORAGE_FOLDER + this.uniqueTaskName + "\\" + key);
        } while (file.exists());

        XStreamUtils.saveAsXml(res, file);
        return key;
    }

    @Override
    public void enforceStore(String key, Object res) throws IOException {
        File file = new File(STORAGE_FOLDER + this.uniqueTaskName + "\\" + key);
        XStreamUtils.saveAsXml(res, file);
    }

    @Override
    public Object load(String key) throws IdentifierNotFoundException, IOException {
        File file = new File(STORAGE_FOLDER + this.uniqueTaskName + "\\" + key);

        if (file.exists()) {
            return XStreamUtils.loadFromXml(file);
        } else {
            throw new IdentifierNotFoundException("Could not load key <" + key + ">, key does not exist!");
        }
    }
}
