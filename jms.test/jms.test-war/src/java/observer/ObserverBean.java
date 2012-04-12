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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("observerBean") 
@SessionScoped
public class ObserverBean implements Serializable{
    private String message;
    
    private void setMessage(String message){
        message = ObserverGateway.UPDATE_TOPIC_NAME;
    }
    private String getMessage(){
        ObserverGateway og;
        og.initialize(Observer observer);
        og.onMessage(Message message);
        og.attach();
        og.update();
        og.detach();
        return message;
    }
}
