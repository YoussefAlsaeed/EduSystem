package services;

import models.Course;
import models.Enrollment;
import models.EnrollmentStatus;
import requests.EnrollmentMessage;
import requests.NotifyRequest;
import util.NotifyMicroSvcUtil;
import util.UserMicroSvcUtil;
import util.StudentEnrollmentProducer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class EnrollmentService {

    private EntityManager entityManager;
    private UserMicroSvcUtil userMicroSvcUtil;
    private NotifyMicroSvcUtil notifyMicroSvcUtil;
    @Inject
    private StudentEnrollmentProducer studentEnrollmentProducer;

    public EnrollmentService() {
        userMicroSvcUtil = new UserMicroSvcUtil();
        notifyMicroSvcUtil = new NotifyMicroSvcUtil();
        studentEnrollmentProducer = new StudentEnrollmentProducer();
        String password = System.getenv("MYSQL_PASSWORD");
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.password",password);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("edu_pu",properties);
        entityManager = emf.createEntityManager();
    }

    public boolean enrollStudent(EnrollmentMessage enrollment) throws JMSException {
        Course course = entityManager.find(Course.class, enrollment.getCourseId());
        if (course == null) {
            System.out.println("Course with id " + enrollment.getCourseId() + " does not exist.");
            return false;
        }
        System.out.println("course with name " + course.getName() +" status is " + course.getContentReviewed());
        if (course.getContentReviewed() != Course.ContentReviewStatus.ACCEPTED) {
            System.out.println("Course content is still under reviewing cant enroll now");
            return false;
        }

        TypedQuery<Enrollment> query = entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.studentId = :studentId AND e.courseId = :courseId AND (e.status != :status1 AND e.status != :status2)",
                Enrollment.class);
        query.setParameter("studentId", enrollment.getStudentId());
        query.setParameter("courseId", enrollment.getCourseId());
        query.setParameter("status1", EnrollmentStatus.REJECTED);
        query.setParameter("status2", EnrollmentStatus.CANCELLED);

        List<Enrollment> existingEnrollments = query.getResultList();

        if (!existingEnrollments.isEmpty()) {
            System.out.println("Student with id " + enrollment.getStudentId() + " already has an active enrollment in course " + enrollment.getCourseId());
            return false;
        }

        if (!userMicroSvcUtil.isUserStudent(enrollment.getStudentId())) {
            System.out.println("User is not authorized to enroll to this course.");
            return false;
        }

        studentEnrollmentProducer.sendMessage(enrollment);
        return true;
    }

    public List<Enrollment> getEnrollments(Long courseId) {
        TypedQuery<Enrollment> query = entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.courseId = :courseId",
                Enrollment.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    public List<Enrollment> getEnrollmentsForStudent(Long studentId) {
        TypedQuery<Enrollment> query = entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.studentId = :studentId",
                Enrollment.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }



    public boolean acceptEnrollment(Long enrollmentId, Long instructorId) {
        Enrollment enrollment = entityManager.find(Enrollment.class, enrollmentId);
        if (enrollment == null) {
            System.out.println("Enrollment with id " + enrollmentId + " does not exist.");
            return false;
        }

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED){
            System.out.println("Enrollment was cancelled by the student");
        }

        Course course = entityManager.find(Course.class, enrollment.getCourseId());
        if (course == null || !course.getInstructorId().equals(instructorId) || !userMicroSvcUtil.isUserInstructor(instructorId)) {
            System.out.println("Instructor with id " + instructorId + " is not authorized to accept this enrollment.");
            return false;
        }

        enrollment.setStatus(EnrollmentStatus.ACCEPTED);
        course.setEnrolledStudents(course.getEnrolledStudents() + 1);

        entityManager.merge(enrollment);

        NotifyRequest request =  new NotifyRequest();
        request.setInstructorId(instructorId);
        request.setStudentId(enrollment.getStudentId());
        String message = "Your enrollment request for course " +course.getName()+ " has been accepted";
        request.setMessage(message);
        notifyMicroSvcUtil.sendNotification(request);
        return true;
    }

    public boolean rejectEnrollment(Long enrollmentId, Long instructorId) {
        Enrollment enrollment = entityManager.find(Enrollment.class, enrollmentId);
        if (enrollment == null) {
            System.out.println("Enrollment with id " + enrollmentId + " does not exist.");
            return false;
        }

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED){
            System.out.println("Enrollment was cancelled by the student");
        }


        Course course = entityManager.find(Course.class, enrollment.getCourseId());
        if (course == null || !course.getInstructorId().equals(instructorId) || !userMicroSvcUtil.isUserInstructor(instructorId)) {
            System.out.println("Instructor with id " + instructorId + " is not authorized to reject this enrollment.");
            return false;
        }

        enrollment.setStatus(EnrollmentStatus.REJECTED);
        entityManager.merge(enrollment);

        NotifyRequest request =  new NotifyRequest();
        request.setInstructorId(instructorId);
        request.setStudentId(enrollment.getStudentId());
        String message = "Your enrollment request for course " +course.getName()+ " has been rejected";
        request.setMessage(message);
        notifyMicroSvcUtil.sendNotification(request);
        return true;
    }


    public boolean cancelEnrollment(Long enrollmentId, Long studentId) {
        Enrollment enrollment = entityManager.find(Enrollment.class, enrollmentId);
        if (enrollment == null) {
            System.out.println("Enrollment with id " + enrollmentId + " does not exist.");
            return false;
        }

        Course course = entityManager.find(Course.class, enrollment.getCourseId());
        if (course == null || !enrollment.getStudentId().equals(studentId) || !userMicroSvcUtil.isUserStudent(studentId)) {
            System.out.println("User is not authorized to cancel this enrollment.");
            return false;
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        entityManager.merge(enrollment);
        return true;
    }
}