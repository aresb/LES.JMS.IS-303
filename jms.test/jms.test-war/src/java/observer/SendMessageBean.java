/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.jms.*;


/**
 *
 * @author are
 */


@Named("sendMessageBean")
public class SendMessageBean {
    @Resource(name="webTrackerConnFactory", mappedName="webTrackerConnFactory")
    public ConnectionFactory connectionFactory;
    
    @Resource(name="webTrackerQueue", mappedName="webTrackerQueue")
    public Destination recieveMessage;
    
    public String messagetext;
    
    
    public void sendMessage(){
        try{
            System.out.println("SendMessage messagetext: " + messagetext);
            
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(recieveMessage);
            TextMessage message = session.createTextMessage();
            message.setText(getMessagetext());
            System.out.println("message.getText(): " + message.getText());
            producer.send(message);
            producer.close();
            session.close();
            connection.close();
            System.out.println("Connection closed");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }
    
    @Remove
    public void remove() {
    messagetext = null;
    }
    
    
}
