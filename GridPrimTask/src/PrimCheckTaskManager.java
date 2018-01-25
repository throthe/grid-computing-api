/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import exception.NotUniqueIdentifierException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.BaseTaskManager;

public class PrimCheckTaskManager extends BaseTaskManager {

    Integer param;
    
    boolean isChecked;
    boolean isAllParamsOut;
    Interval interval;
    int intervalCount = 5;

    public PrimCheckTaskManager() {
        super();

        isAllParamsOut = false;
        isChecked = false;
        param = Integer.MAX_VALUE - 1;
        
        interval = new Interval();
        interval.init(0, 1000, param);
    }

    @Override
    public void finishedResult(HashMap<String, Object> params, Object result) {
        
        try {
            
            getStorageManager().store(params.toString(), result);
            intervalCount--;
            
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (NotUniqueIdentifierException ex) {
            Logger.getLogger(PrimCheckTaskManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public HashMap<String, Object> getNextParams() {
        
        if (!isAllParamsOut) {
            HashMap<String, Object> next = new HashMap<>();

            this.interval = interval.getNext();

            next.put("interval", this.interval);

            if (interval.getCurrentPosition() >= interval.getEnd()) {
                isAllParamsOut = true;
            }

            return next;

        } else {
            return null;
        }
    }

    @Override
    public int getTaskProgress() {
        return 0;
    }

    @Override
    public boolean isStatusQueryAccepted() {
        return true;
    }
}
