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
public interface IParameterManager {

    public HashMap<String, Object> paramSolved(String clientId);
    
    public boolean isParamFinished(String clientId);
    
    public boolean isTaskFinished();
    
    public HashMap<String, Object> receiveParam(String clientId); 
    
    public void abortClient(String clientId);
  
    public void cleanup();
    
    public int getNumberOfWorkingClients(); 
}
