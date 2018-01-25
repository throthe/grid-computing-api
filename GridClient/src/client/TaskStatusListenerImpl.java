package client;

import interfaces.ITaskStatusListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
public class TaskStatusListenerImpl implements ITaskStatusListener {

    @Override
    public void setProgress(int progress) {
        System.out.println("Progress [" + progress + "%]");
    }

    @Override
    public void print(Object obj) {
        System.out.println(obj.toString());
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj.toString());
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
    }

}
