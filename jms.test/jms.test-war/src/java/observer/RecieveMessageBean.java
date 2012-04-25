/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDrivenContext;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author are
 */

@MessageDriven(mappedName="webTrackerQueue")
        /*activationConfig = {
            @ActivationConfigProperty(
                propertyName="webTrackerQueue",
                propertyValue="webTrackerQueue")})*/

@Named("recieveMessageBean")



public class RecieveMessageBean implements MessageListener{
    public String messagetext;
    @Resource
    public MessageDrivenContext mdc;
    
    @Override
    public void onMessage(Message message){
        try{
            TextMessage textmessage = (TextMessage) message;
            messagetext = textmessage.getText();
            System.out.println("textmessage.getText() utført: " + textmessage.getText());
        }catch(Exception e){
            e.printStackTrace();
            mdc.setRollbackOnly();
        }finally{
        setMessagetext(messagetext);
        System.out.println("setMessageText utført. Print that shiet!: " + messagetext);
        }
    }
    
    public void setMessagetext(String messagetext){
        this.messagetext = messagetext;
    }
    
    public String getMessagetext(){
        return messagetext;
    }
    	
}
