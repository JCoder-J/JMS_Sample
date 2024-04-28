package com.home.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.jms.service.ActiveMQMessageService;
import com.home.jms.to.JMSRequest;
import com.home.jms.to.JMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/jmstest")
public class ActiveMQController {

    @Autowired
    ActiveMQMessageService producer;

    @GetMapping(path="get")
    public ResponseEntity<String> getMessage(){
        String response = "";
        try{
        ObjectMapper mapper = new ObjectMapper();
        response = mapper.writeValueAsString(new JMSResponse(producer.processMessage("get", null)));
        } catch (JsonProcessingException e){
            e.getMessage();
        }
        if(StringUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(path="send")
    public ResponseEntity<String> sendMessage(@RequestBody String request){
        String response = "";
        String message = "";

        try{
            ObjectMapper mapper = new ObjectMapper();
            JMSRequest payload = mapper.readValue(request, JMSRequest.class);
            response = mapper.writeValueAsString(new JMSResponse(producer.processMessage("send", payload)));
        } catch (JsonProcessingException e){
            e.getMessage();
        }
        if(StringUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
