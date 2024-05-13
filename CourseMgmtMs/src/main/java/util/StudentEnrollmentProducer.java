package util;

import models.Enrollment;
import requests.EnrollmentMessage;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class StudentEnrollmentProducer {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/queue/enrollQueue")
    private Queue queue;

    public void sendMessage(EnrollmentMessage enrollment) throws JMSException {
        System.out.println(queue.getQueueName());
        Session session;
        MessageProducer messageProducer;
        try (Connection connection = connectionFactory.createConnection()) {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = queue;
            messageProducer = session.createProducer(destination);
            connection.start();
            ObjectMessage msg = session.createObjectMessage();
            msg.setObject(enrollment);
            messageProducer.send(msg);
            System.out.println("Enrollment object sent to the queue: " + enrollment);
        }
    }
}
