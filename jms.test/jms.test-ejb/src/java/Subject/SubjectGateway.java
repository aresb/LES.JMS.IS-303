package Subject;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.NamingException;


public class SubjectGateway {

    
	public static final String UPDATE_TOPIC_NAME = "jms/Update";
	private Connection connection;
	private Session session;
	private MessageProducer updateProducer;
        
        @Resource(mappedName="webTrackerConnFactory")
        private QueueConnectionFactory qFactory;

        @Resource(mappedName="webTrackerQueue")
        private Queue queue;

	public SubjectGateway() {
		super();
	}

	public SubjectGateway newGateway() throws JMSException, NamingException {
		SubjectGateway gateway = new SubjectGateway();
		gateway.initialize();
		return gateway;
	}

	public void initialize() throws JMSException, NamingException {
		ConnectionFactory connectionFactory = qFactory;
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination updateTopic = queue;
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