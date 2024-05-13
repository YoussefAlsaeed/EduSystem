package util;

import models.Enrollment;
import requests.EnrollmentMessage;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

@MessageDriven(name = "enrollQueue",activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/enrollQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class StudentEnrollmentMDB implements MessageListener {
    private EntityManager entityManager;
    public StudentEnrollmentMDB() {

        String password = System.getenv("MYSQL_PASSWORD");
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.password",password);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu_pu",properties);
        entityManager = emf.createEntityManager();
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                EnrollmentMessage enrollmentMessage = (EnrollmentMessage) objectMessage.getObject();
                Enrollment enrollment = new Enrollment();
                enrollment.setCourseId(enrollmentMessage.getCourseId());
                enrollment.setStudentId(enrollmentMessage.getStudentId());
                enrollment.setStatus(enrollmentMessage.getStatus());
                entityManager.persist(enrollment);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}