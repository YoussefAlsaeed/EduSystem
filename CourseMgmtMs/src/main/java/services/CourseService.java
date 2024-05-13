package services;

import models.*;
import util.UserMicroSvcUtil;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CourseService {

    private EntityManager entityManager;
    private UserMicroSvcUtil userMicroSvcUtil;

    public CourseService() {
        userMicroSvcUtil = new UserMicroSvcUtil();
        String password = System.getenv("MYSQL_PASSWORD");
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.password",password);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu_pu",properties);
        entityManager = emf.createEntityManager();
    }

    public boolean createCourse(Course course) {
        Course existingCourse = findCourseByName(course.getName());

        if (existingCourse != null) {
            System.out.println("Course with name: " + existingCourse.getName() + " already exists.");
            return false;
        }

        if (userMicroSvcUtil.isUserInstructor(course.getInstructorId())) {
            entityManager.getTransaction().begin();
            course.setContentReviewed(Course.ContentReviewStatus.PENDING);
            course.setRating(0);
            course.setEnrolledStudents(0);
            course.setTotalRates(0);
            entityManager.persist(course);
            entityManager.getTransaction().commit();
            return true; // Course created successfully
        } else {
            return false; // Failed to create course
        }
    }

    public boolean editCourseName(Long courseId, String newName, Long adminId) {
        Course course = entityManager.find(Course.class, courseId);

        if (course == null) {
            System.out.println("Course with ID: " + courseId + " not found.");
            return false;
        }

        // Check if the new course name already exists
        Course existingCourse = findCourseByName(newName);
        if (existingCourse != null && !existingCourse.getCourseId().equals(courseId)) {
            System.out.println("Course with name: " + newName + " already exists.");
            return false;
        }
        //Checking if user is authorized
        if (!userMicroSvcUtil.isUserAdmin(adminId) ) {
            System.out.println("User is not authorized to edit this course.");
            return false;
        }

        // Update the course name
        entityManager.getTransaction().begin();
        course.setName(newName);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean deleteCourse(Long courseId, Long adminId) {
        Course course = entityManager.find(Course.class, courseId);

        if (course == null) {
            System.out.println("Course with ID: " + courseId + " not found.");
            return false;
        }

        if (!userMicroSvcUtil.isUserAdmin(adminId)) {
            System.out.println("User is not authorized to delete this course.");
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(course);
        entityManager.getTransaction().commit();
        return true;
    }

    // for admin
    public List<Course> getAllCourses() {
        Query query = entityManager.createQuery("SELECT c FROM Course c", Course.class);
        return query.getResultList();
    }

    // for student + instructor
    public List<Course> getAllReviewedCourses() {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.contentReviewed = :status", Course.class);
        query.setParameter("status", Course.ContentReviewStatus.ACCEPTED);
        return query.getResultList();
    }

    // instructor

    public List<Course> getCoursesByInstructor(Long instructorId) {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.instructorId = :instructorId", Course.class);
        query.setParameter("instructorId", instructorId);
        return query.getResultList();
    }

    // for student + instructor

    public List<Course> searchCoursesByName(String name) {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.name LIKE :name AND c.contentReviewed = :status", Course.class);
        query.setParameter("name", "%" + name + "%");
        query.setParameter("status", Course.ContentReviewStatus.ACCEPTED);
        return query.getResultList();
    }

    // for student + instructor
    public List<Course> searchCoursesByCategory(String category) {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.category LIKE :category AND c.contentReviewed = :status", Course.class);
        query.setParameter("category", "%"+category + "%");
        query.setParameter("status", Course.ContentReviewStatus.ACCEPTED);
        return query.getResultList();
    }

    // for student + instructor
    public List<Course> sortByRatings() throws JMSException {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.contentReviewed = :status ORDER BY c.rating DESC", Course.class);
        query.setParameter("status", Course.ContentReviewStatus.ACCEPTED);
        return query.getResultList();
    }

    // instructor
    public List<Course> sortByRatingsInstructor(Long instructorId) {
        Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.instructorId = :instructorId AND c.contentReviewed = :status ORDER BY c.rating DESC", Course.class);
        query.setParameter("instructorId", instructorId);
        query.setParameter("status", Course.ContentReviewStatus.ACCEPTED);
        return query.getResultList();
    }

    public boolean rejectCourseContent(Long courseId, Long adminId) {
        Course course = entityManager.find(Course.class, courseId);

        if (course == null) {
            System.out.println("Course with ID: " + courseId + " not found.");
            return false;
        }

        // Checking if user is authorized
        if (!userMicroSvcUtil.isUserAdmin(adminId)) {
            System.out.println("User is not authorized to reject course content.");
            return false;
        }

        // Update the course content review status to REJECTED
        entityManager.getTransaction().begin();
        course.setContentReviewed(Course.ContentReviewStatus.REJECTED);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean acceptCourseContent(Long courseId, Long adminId) {
        Course course = entityManager.find(Course.class, courseId);

        if (course == null) {
            System.out.println("Course with ID: " + courseId + " not found.");
            return false;
        }

        // Checking if user is authorized
        if (!userMicroSvcUtil.isUserAdmin(adminId)) {
            System.out.println("User is not authorized to reject course content.");
            return false;
        }

        // Update the course content review status to REJECTED
        entityManager.getTransaction().begin();
        course.setContentReviewed(Course.ContentReviewStatus.ACCEPTED);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean rateCourse(Long courseId, double rating) {
        Course course = entityManager.find(Course.class, courseId);

        if (course == null) {
            System.out.println("Course with ID: " + courseId + " not found.");
            return false;
        }

        // Check if the rating is valid
        if (rating < 0 || rating > 10) {
            System.out.println("Invalid rating. Rating must be between 0 and 10.");
            return false;
        }

        if (course.getContentReviewed() != Course.ContentReviewStatus.ACCEPTED) {
            System.out.println("Course content is not yet accepted. Cannot add rate.");
            return false;
        }

        entityManager.getTransaction().begin();
        // Increment total rates
        int totalRates = course.getTotalRates() + 1;
        course.setTotalRates(totalRates);

        // Calculate new average rating
        double newRating = (course.getRating() + rating) / totalRates;
        course.setRating(newRating);

        entityManager.getTransaction().commit();
        return true;
    }

    public boolean addReview(Reviews review) {
        Course course = entityManager.find(Course.class, review.getCourseId());

        if (!userMicroSvcUtil.isUserStudent(review.getStudentId())) {
            System.out.println("User is not authorized to add review to course .");
            return false;
        }

        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        if (course.getContentReviewed() != Course.ContentReviewStatus.ACCEPTED) {
            System.out.println("Course content is not yet accepted. Cannot add review.");
            return false;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(review);
        entityManager.getTransaction().commit();
        return true;
    }

    private Course findCourseByName(String name){
        try {
            Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.name = :name", Course.class);
            query.setParameter("name", name);
            return (Course) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
