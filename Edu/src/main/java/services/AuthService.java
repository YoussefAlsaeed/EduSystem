package services;

import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.util.List;

@Stateless
@Named
public class AuthService {
    private EntityManager entityManager;

    public AuthService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ejb_pu");
        entityManager = emf.createEntityManager();
    }

    public boolean registerUser(User user) {
        try {
            entityManager.getTransaction().begin();

            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", user.getEmail());
            System.out.println(user.getEmail());
            List<User> existingUsers = query.getResultList();

            if (!existingUsers.isEmpty()) {
                System.out.println("User already exists!");
                return false;
            }

            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            entityManager.getTransaction().begin();

            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<User> users = query.getResultList();

            if (users.isEmpty()) {
                System.out.println("Invalid email or password!");
                return false;
            } else {
                System.out.println("Login successful!");
                return true;
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
