package com.indix.exceptions;

/**
 * Created by cloudera on 8/10/17.
 */
public class ReplicateException extends Exception {
    public ReplicateException(){
        System.out.println("Not connected to Replica Server");
    }

    public ReplicateException(String message){
        System.out.println(message);
    }
}
