package controllers;

import io.swagger.annotations.Api;
import models.Enrollment;
import requests.EnrollmentMessage;
import services.EnrollmentService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/enroll")
@Stateless
@Api(value = "Enrollment service")
public class EnrollmentController {

    @Inject
    EnrollmentService enrollmentService;

    @POST
    @Path("/course")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(EnrollmentMessage enrollment) throws JMSException {
        boolean created = enrollmentService.enrollStudent(enrollment);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity("Enrollment created successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Enrollment couldn't be created !!")
                    .build();
        }
    }

    @GET
    @Path("/getEnrollments/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnrollments(@PathParam("courseId") Long courseId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollments(courseId);
        return Response.status(Response.Status.OK)
                .entity(enrollments)
                .build();
    }

    @PUT
    @Path("/acceptEnrollment/{enrollmentId}/{instructorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptEnrollment(@PathParam("enrollmentId") Long enrollmentId, @PathParam("instructorId") Long instructorId) {
        boolean accepted = enrollmentService.acceptEnrollment(enrollmentId, instructorId);
        if (accepted) {
            return Response.status(Response.Status.OK)
                    .entity("Enrollment accepted successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Enrollment couldn't be accepted.")
                    .build();
        }
    }

    @PUT
    @Path("/rejectEnrollment/{enrollmentId}/{instructorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectEnrollment(@PathParam("enrollmentId") Long enrollmentId, @PathParam("instructorId") Long instructorId) {
        boolean rejected = enrollmentService.rejectEnrollment(enrollmentId, instructorId);
        if (rejected) {
            return Response.status(Response.Status.OK)
                    .entity("Enrollment rejected successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Enrollment couldn't be rejected.")
                    .build();
        }
    }

    @PUT
    @Path("/cancelEnrollment/{enrollmentId}/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelEnrollment(@PathParam("enrollmentId") Long enrollmentId, @PathParam("studentId") Long studentId) {
        boolean cancelled = enrollmentService.cancelEnrollment(enrollmentId, studentId);
        if (cancelled) {
            return Response.status(Response.Status.OK)
                    .entity("Enrollment cancelled successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Enrollment couldn't be cancelled.")
                    .build();
        }
    }

    @GET
    @Path("/getEnrollmentsForStudent/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnrollmentsForStudent(@PathParam("studentId") Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
        return Response.status(Response.Status.OK)
                .entity(enrollments)
                .build();
    }

}
