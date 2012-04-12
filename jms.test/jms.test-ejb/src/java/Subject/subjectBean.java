package Subject;


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
}
