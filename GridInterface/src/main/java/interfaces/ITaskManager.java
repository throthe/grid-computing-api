/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.HashMap;

/**
 *
 * @author thomas_r
 */
public interface ITaskManager {

    public HashMap<String, Object> getNextParams();

    public void finishedResult(HashMap<String, Object> params, Object res);

    public boolean isStatusQueryAccepted();

    public int getTaskProgress();
    
}
