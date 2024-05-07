package services;

import models.Admin;
import models.Instructor;
import models.Student;
import models.User;
import requests.UserRegisterationRequest;

import javax.ejb.Stateless;
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

    public boolean registerUser(UserRegisterationRequest user) {
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

            User persistUser;

            switch (user.getUserType()) {
                case Admin:
                    persistUser = new Admin();
                    break;
                case Instructor:
                    persistUser = new Instructor();
                   ((Instructor) persistUser).setYearsOfExperience(user.getYearsOfExperience());
                    break;
                case Student:
                    persistUser = new Student();
                    break;
                default:
                    System.out.println("Unsupported user type!");
                    return false;
            }

            persistUser.setName(user.getName());
            persistUser.setEmail(user.getEmail());
            persistUser.setAffiliation(user.getAffiliation());
            persistUser.setPassword(user.getPassword());
            persistUser.setBio(user.getBio());

            entityManager.persist(persistUser);
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
