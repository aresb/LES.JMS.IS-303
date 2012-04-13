package observer;



//import Subject.SubjectGateway;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * @author Kjersti
 */

@Named(value = "subjectBean")
@SessionScoped
public class subjectBean implements Serializable {
  private String message;
  
  public subjectBean() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void sendMessage(){
        
        try {
            SubjectGateway s = new SubjectGateway();
            s.newGateway();
            s.initialize();
            s.notify(getMessage());
            s.release();
    }
        catch (Exception e){
            System.out.println ("Message failed during sending");
           
            
            
        }
    }
    
    
}
