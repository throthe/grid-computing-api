package interfaces;

import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
public interface IServerParameterManager {

    public void newParam(String clientId, HashMap<String, Object> params);

    public HashMap<String, Object> receiveDuplicateParam(String clientId);

    public HashMap<String, Object> registerClient(String clientId, Long paramId);
}
