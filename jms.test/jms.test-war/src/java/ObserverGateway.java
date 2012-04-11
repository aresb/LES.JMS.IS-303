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


public class ObserverGateway implements MessageListener {

	public static final String UPDATE_TOPIC_NAME = "jms/Update";
	private Observer observer;
	private Connection connection;
	private MessageConsumer updateConsumer;
                
        @Resource(name="connFactory", mappedName="webTrackerConnFactory")
        private QueueConnectionFactory qFactory;

        @Resource(name="jmsQueue", mappedName="webTrackerQueue")
        private Queue queue;

	protected ObserverGateway() {
		super();
	}

	public static ObserverGateway newGateway(Observer observer)
		throws JMSException, NamingException {
		ObserverGateway gateway = new ObserverGateway();
		gateway.initialize(observer);
		return gateway;
	}

	protected void initialize(Observer observer) throws JMSException, NamingException {
		this.observer = observer;   
		ConnectionFactory connectionFactory = qFactory;
		connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination updateTopic = queue;
		updateConsumer = session.createConsumer(updateTopic);
		updateConsumer.setMessageListener(this);
	}

	public void onMessage(Message message) {
		try {
			TextMessage textMsg = (TextMessage) message; // assume cast always works
			String newState = textMsg.getText();
			update(newState);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void attach() throws JMSException {
		connection.start();
	}

	public void detach() throws JMSException {
		if (connection != null) {
			connection.stop();
			connection.close();
		}
	}
        //TODO: Denne metoden skal hente oppdateringer fra SubjectGateway
	private void update(String newState) throws JMSException {
            Observable o = null;
            Object arg = null;
            observer.update(o, arg);
	}
}