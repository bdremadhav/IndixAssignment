package com.indix.controller;

import com.indix.dao.ReplicateDAO;
import com.indix.exceptions.ReplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Madhav on 8/8/17.
 */
@Controller
public class KVController {
    private Map<String,String> map = new HashMap<String,String>();

    @Autowired
    ReplicateDAO replicateDAO;

    @RequestMapping(value = {"/set/{key}", "/set/{key}/"}, method = RequestMethod.POST)
    public ResponseEntity<String> putValue(@PathVariable("key") String key, HttpServletRequest request) throws Exception {

        String value = request.getReader().lines().collect(Collectors.joining());
        try {
            map.put(key,value);
            int requestPort = request.getServerPort();
            boolean ackFromReplica = replicateDAO.replicateData(requestPort,key,value);

            if(ackFromReplica) {
                return new ResponseEntity<String>("OK",HttpStatus.CREATED);
            }
            else {
                throw new ReplicateException("Bad Response from Replicated Process");
            }

        }catch (IOException e){
            e.printStackTrace();
            throw new IOException();
        }

    }

    @RequestMapping(value = {"/get/{key}","/get/{key}/"}, method = RequestMethod.GET)
    public ResponseEntity<String> getValue(@PathVariable("key") String key){
        String value = map.get(key);
        System.out.println("In get method value is= " + value);
        if(value != null){
            return new ResponseEntity<String>(value,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("No Key Found",HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/replicate/put/{key}", method = RequestMethod.POST)
    public ResponseEntity<Void> replicateValue(@PathVariable("key") String key,HttpServletRequest request) throws IOException{
        try {
            String value = request.getReader().lines().collect(Collectors.joining());
            map.put(key,value);
            System.out.println("map in replica method = " + map);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            throw new IOException("Unable to read value in Replicate Node");
        }

    }


}
