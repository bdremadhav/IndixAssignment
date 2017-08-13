package com.indix.dao;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Madhav on 8/11/17.
 */
public class ReplicateDAOTest {

    @Test
    public void getPortsListFromConfigTest(){
        try {
            ReplicateDAO replicateDAO = new ReplicateDAO();
            System.out.println("List of Ports configured = " + Arrays.toString(replicateDAO.getPortsListFromConfig("/config.properties")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getReplicaPortsTest(){
        try {
            ReplicateDAO replicateDAO = new ReplicateDAO();
            System.out.println("List of Ports where data should be replicated = " + replicateDAO.getReplicaPorts(4455));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void replicateDataTest(){
        try {
            ReplicateDAO replicateDAO = new ReplicateDAO();
            System.out.println("Is data Replicated to all ports = " + replicateDAO.replicateData(4455,"key","value"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
