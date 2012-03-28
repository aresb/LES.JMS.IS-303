import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class SubjectGateway {

	public static final String UPDATE_TOPIC_NAME = "jms/Update";
	private Connection connection;
	private Session session;
	private MessageProducer updateProducer;

	protected SubjectGateway() {
		super();
	}

	public static SubjectGateway newGateway() throws JMSException, NamingException {
		SubjectGateway gateway = new SubjectGateway();
		gateway.initialize();
		return gateway;
	}

	protected void initialize() throws JMSException, NamingException {
		ConnectionFactory connectionFactory = JndiUtil.getQueueConnectionFactory();
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination updateTopic = JndiUtil.getDestination(UPDATE_TOPIC_NAME);
		updateProducer = session.createProducer(updateTopic);

		connection.start();
	}

	public void notify(String state) throws JMSException {
		TextMessage message = session.createTextMessage(state);
		updateProducer.send(message);
	}

	public void release() throws JMSException {
		if (connection != null) {
			connection.stop();
			connection.close();
		}
	}
}