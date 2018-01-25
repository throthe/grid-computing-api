/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import java.io.Serializable;

public class Interval implements Serializable {

    private static final long serialVersionUID = -1294137606626639985L;

    private int begin;
    private int end;
    private int max;
    private int position;

    private int step;

    private Interval(int step) {
        this.step = step;
    }

    public Interval() {
        this(1);
    }

    public void init(int begin, int end, int max) {
        this.begin = begin;
        this.end = end;
        this.max = max;
        this.position = begin;
    }

    public Interval getNext() {
        
        int diff = Math.abs(this.end - this.begin);
        
        Interval next = new Interval();
        next.begin = this.begin + diff;
        next.end = next.begin + diff;
        next.max = this.max;
        next.position = next.begin;

        return next;
    }

    public int getCurrentPosition() {
        if(this.position == this.end) 
            return this.end;
        return this.position++;
    }

    public int getStart() {
        return this.begin;
    }
    
    public int getMax() {
        return this.max;
    }

    public int getEnd() {
        return this.end;
    }

    public void setStep(int range) {
        this.step = range;
    }

    public int increasePosition() {
        this.position += step;

        if (this.position >= this.end) {
            this.position = end;
        }
        return position;
    }

    @Override
    public String toString() {
        return "[" + begin + ";" + end + "]";
    }

}
