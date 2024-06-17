package services;

import models.Logs;
import util.UserMicroSvcUtil;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TrackService {

    private EntityManager entityManager;
    private UserMicroSvcUtil userMicroSvcUtil;

    public TrackService() {
        userMicroSvcUtil = new UserMicroSvcUtil();
        String password = System.getenv("MYSQL_PASSWORD");
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.password",password);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu_pu",properties);
        entityManager = emf.createEntityManager();
    }

    public List<Logs> getActivityUsage(Long adminId)
    {
        if (!userMicroSvcUtil.isUserAdmin(adminId))
            return null;

        Query query = entityManager.createQuery("SELECT c FROM Logs c", Logs.class);
        return query.getResultList();
    }
}
