package Subject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.Observer;
import java.util.Observable;
import javax.annotation.Resource;
import javax.jms.*;
import java.lang.Object;


public class ObserverGateway implements MessageListener {
        
        //Denne er kanskje feil. Endret fra "jms/update"
	public static final String UPDATE_TOPIC_NAME = "webTrackerQueue";
        
	public Observer observer;
        public Observable observable;
	private Connection connection;
	private MessageConsumer updateConsumer;
        public String newState;
        public Object obj;
                
        @Resource(name="connFactory", mappedName="webTrackerConnFactory")
        private QueueConnectionFactory qFactory;

        @Resource(name="jmsQueue", mappedName="webTrackerQueue")
        private Queue queue;

	public ObserverGateway() {
		super();
	}

	public static ObserverGateway newGateway(Observer observer)
		throws JMSException, NamingException {
           
		ObserverGateway gateway = new ObserverGateway();
		gateway.initialize(observer);
          
		return gateway;
	}

	public void initialize(Observer observer) throws JMSException, NamingException {
		this.observer = observer;   
		ConnectionFactory connectionFactory = qFactory;
		connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination updateTopic = queue;
		updateConsumer = session.createConsumer(updateTopic);
		updateConsumer.setMessageListener(this);
	}
        @Override
	public void onMessage(Message message) {
		try {
			TextMessage textMsg = (TextMessage) message; // assume cast always works
			newState = textMsg.getText();
			update(newState);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void attach() throws JMSException {
            try{
		connection.start();
            }catch(JMSException jmsx){
                System.out.println(jmsx);
            }
	}

	public void detach() throws JMSException {
            try{
                if (connection != null) {
			connection.stop();
			connection.close();
		}
            }catch(JMSException jmsx3){
            System.out.println(jmsx3);
            }
	}
        //TODO: Denne metoden skal hente oppdateringer fra SubjectGateway
	private void update(String newState) throws JMSException {
            //try{
            observer.update(observable, obj);
            /*}catch(JMSException jmsx2){
             *   System.out.println(jmsx2);
            *}*/
        }

}
