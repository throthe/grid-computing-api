package interfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
public interface ITaskStatusListener {

    public void setProgress(int progress);

    public void print(Object obj);

    public void println(Object obj);

    public void clear();
}
