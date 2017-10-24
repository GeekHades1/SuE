package org.hades.sue.bean;

/**
 * Created by Hades on 2017/10/24.
 */

public class RData<T>{
    public int state ;
    public String msg;
    public T data;


    @Override
    public String toString() {
        return "RData{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
