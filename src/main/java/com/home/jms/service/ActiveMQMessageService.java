package com.home.jms.service;

import com.home.jms.to.JMSRequest;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Enumeration;


@Component
public class ActiveMQMessageService {
    //URL of the JMS server.
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "just_sample_QUEUE";

    public String processMessage(String process, JMSRequest payload) {
        String serverResponse = null;
        // Getting JMS connection from the server and starting it
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //Creating a session to send/receive JMS message.
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);


            //The queue will be created automatically on the server.
            Destination destination = session.createQueue(jmsQueue);
            
            switch (process){
                case "send" : serverResponse = sendMessage(session, destination, payload);
                            break;
                case "get"  : serverResponse = getMessage(session, destination);
                            break;
                default     : serverResponse = null;
                            break;
                             
            }
            connection.close();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            return serverResponse;
        }
    }

    private String sendMessage(Session session, Destination destination, JMSRequest payload){
        String sendingStatus = "";
        String message = payload.getMessage();
        try {
            // MessageProducer is used for sending messages to the queue.
            MessageProducer producer = session.createProducer(destination);
            // We will send a small text message with small Json'
            TextMessage textMessage = session
                    .createTextMessage(message);

            // Here we are sending our message!
            producer.send(textMessage);

            sendingStatus = "JMS Message Sent successfuly:: " + message;
        } catch (JMSException e) {
            sendingStatus = "Error in sending message";
            throw new RuntimeException(e);
        }

        return sendingStatus;
    }

    private String getMessage(Session session, Destination destination){
        String receivingStatus = "";
        try {
            QueueBrowser browser = session.createBrowser((Queue) destination);
            Enumeration elems = browser.getEnumeration();
            if(elems.hasMoreElements()){
                System.out.println("has queue");
                // MessageConsumer is used for receiving (consuming) messages
                MessageConsumer consumer = session.createConsumer(destination);

                // Here we receive the message.
                Message message = consumer.receive();

                //We will be using TestMessage in our example. MessageProducer sent us a TextMessage
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    receivingStatus = textMessage.getText();
                }
            } else {
                receivingStatus = "No messages in queue";
                System.out.println(receivingStatus);
            }
        } catch (JMSException e) {
            receivingStatus = "Error in receiving message";
            throw new RuntimeException(e);
        }
        return receivingStatus;
    }
}
