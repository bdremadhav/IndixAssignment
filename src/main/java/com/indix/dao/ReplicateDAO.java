package com.indix.dao;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Madhav on 8/7/17.
 */

@Repository
public class ReplicateDAO{

    private static final String PROP_NAME_LIST_OF_PORTS = "ListofPorts";

    public  String[] getPortsListFromConfig(String configFileName) throws IOException{
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream(configFileName));
        String[] configuredPorts = properties.getProperty(PROP_NAME_LIST_OF_PORTS).split(",");
        return configuredPorts;
    }


    public List<Integer> getReplicaPorts(int requestPort) throws IOException {
        String[] configuredPorts = getPortsListFromConfig("/config.properties");
        List<Integer> replicaPorts = new ArrayList<Integer>();
        for(String port : configuredPorts){
            if(!port.equals(String.valueOf(requestPort))){
                replicaPorts.add(Integer.parseInt(port));
            }
        }
        return replicaPorts;
    }

    public boolean replicateData(int requestPort, String key, String value) throws IOException {
        List<Integer> replicaFailurePorts = new ArrayList<Integer>();
        List<Integer> replicaPortsList = getReplicaPorts(requestPort);
        if(replicaPortsList.size() == 0){
            return true;
        }

        else {
            for (Integer replicaPort : replicaPortsList) {
                try {
                    String replicaURL = "http://localhost:" + replicaPort + "/replicate/put/" + key;
                    URL url = new URL(replicaURL);
                    HttpURLConnection replicaConn = (HttpURLConnection) url.openConnection();
                    replicaConn.setRequestMethod("POST");
                    replicaConn.setDoOutput(true);
                    OutputStreamWriter outputStream = new OutputStreamWriter(replicaConn.getOutputStream());
                    outputStream.write(value);
                    outputStream.flush();
                    int responseCodeFromReplica = replicaConn.getResponseCode();
                    System.out.println("responseCodeFromReplica = " + responseCodeFromReplica);
                    if (responseCodeFromReplica != 200) {
                        replicaFailurePorts.add(replicaPort);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new MalformedURLException("Url is not correct");
                } catch (IOException io) {
                    io.printStackTrace();
                    throw new IOException("IOException while Replicating");
                }
            }
        }
        return (replicaFailurePorts.size() > 0) ? false : true;
    }
}
