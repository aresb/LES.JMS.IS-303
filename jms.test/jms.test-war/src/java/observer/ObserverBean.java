/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;


/**
 *
 * @author are
 */
//import Subject.ObserverGateway;
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
        this.beanmessage = beanmessage;
    }
    
    private Message getBeanMessage() throws JMSException, NamingException{
        try{
             ObserverGateway og = new ObserverGateway();
             
             try{
                og.attach(); 
                og.newGateway(observer);
                assert og != null;
             }catch(NamingException ne){
                System.out.println(ne);
             }
            
                
                og.onMessage(message);
                og.detach();
             
        }catch(JMSException jmsx4){
            System.out.println(jmsx4);
            
        }
        
        return message;
    }
}

