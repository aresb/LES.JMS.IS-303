/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;


/**
 *
 * @author are
 */
import java.io.Serializable;
import java.util.Observer;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

@Named("observerBean") 
@SessionScoped
public class ObserverBean implements Serializable{
    
    private String beanmessage;
    private Observer observer;
    private Message message;
    
    private void setMessage(String beanmessage){
        beanmessage = ObserverGateway.UPDATE_TOPIC_NAME;
    }
    
    private String getBeanMessage() throws JMSException, NamingException{
        try{
             ObserverGateway og = null;
             try{
                og.newGateway(observer);
             }catch(NamingException ne){
             System.out.println(ne);
             }
            
                og.attach(); 
                og.onMessage(message);
                og.detach();
             
        }catch(JMSException jmsx4){
            System.out.println(jmsx4);
            
        }
        
        return beanmessage;
    }
}

