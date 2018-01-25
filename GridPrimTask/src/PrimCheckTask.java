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
import util.UserTask;

public class PrimCheckTask extends UserTask {

    private List<Integer> primeNumbers;

    private static final long serialVersionUID = 1L;

    @Override
    public Object getResult() {
        PrimCheckResult Result = new PrimCheckResult();
        Result.primeNumbers = this.primeNumbers;
        return Result;
    }

    @Override
    public void run() {

        Interval interval = (Interval) this.getParameterByName("interval");
        
        if(primeNumbers == null)
            primeNumbers = new ArrayList<>();
        
        //ITaskStatusListener observer = this.getObserver();

        System.out.println("Prim-Check <" + interval.getMax() + ">");
        System.out.println("Interval [" + interval.getStart() + ":" + interval.getEnd() + "]");

        for(int i = interval.getStart(); i <= interval.getEnd(); i++) {
            boolean isPrimNumber = isPrime(i);
            
            if (isPrimNumber) {
                primeNumbers.add(i);
                //System.out.println("Prim found <" + i + ">");
            }
        }
    }
    
    private boolean isPrime(int n) {
        if(n <= 1) {
            return false;
        } else if(n <= 3) {
            return true;
        } else if(n % 2 == 0 || n % 3 == 0) {
            return false;
        } else {
            int i = 5;
            while ( i * i <= n) {
                if(n % i == 0 || n % (i+2) == 0) {
                    return false;
                }
                i = i + 6;
            }
            return true;
        }
    }

    @Override
    public boolean isProgressBarRequired() {
        return true;
    }

    @Override
    public boolean isTextOutputAreaRequired() {
        return true;
    }

    @Override
    public int getMaxProgressValue() {
        return 0;
    }

}
