//package services;
//
//import models.Course;
//import javax.ejb.Stateful;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.util.HashMap;
//import java.util.Map;
//
//@Stateful
//public class CourseRatingService{
//    private EntityManager entityManager;
//    private int totalRating;
//    private int numberOfRatings;
//
//    public CourseRatingService() {
//        String password = System.getenv("MYSQL_PASSWORD");
//        Map<String, String> properties = new HashMap<>();
//        properties.put("javax.persistence.jdbc.password",password);
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu_pu",properties);
//        entityManager = emf.createEntityManager();
//    }
//
//    public void rateCourse(Long courseId, int rating) {
//        totalRating += rating;
//        numberOfRatings++;
//
//        // Calculate the average rating
//        double averageRating = (double) totalRating / numberOfRatings;
//
//        // Update the course entity with the new average rating
//        Course course = entityManager.find(Course.class, courseId);
//        if (course != null) {
//            course.setRating(averageRating);
//            entityManager.merge(course);
//        }
//    }
//}
