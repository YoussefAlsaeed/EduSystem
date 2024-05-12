package controllers;

import io.swagger.annotations.Api;
import models.Course;
import models.Reviews;
import requests.CourseEditRequest;
import requests.DeleteCourseRequest;
import services.CourseService;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/course")
@Stateless
@Api(value = "Course service")

public class CourseController {

    private CourseService courseService = new CourseService();

    @POST
    @Path("/createCourse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(Course course) {
        boolean created = courseService.createCourse(course);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity("Course " + course.getName() + " created successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Course " + course.getName() + " couldn't be created !!")
                    .build();
        }
    }

    @PUT
    @Path("/editCourse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCourseName(CourseEditRequest request) {
        boolean updated = courseService.editCourseName(request.getCourseId(), request.getNewName(), request.getAdminId());
        if (updated) {
            return Response.status(Response.Status.OK)
                    .entity("Course name updated successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Failed to update course name.")
                    .build();
        }
    }
    @PUT
    @Path("/acceptContent/{courseId}/{adminId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptCourseContent(@PathParam("courseId")Long courseId,@PathParam("adminId") Long adminId) {
        boolean done = courseService.acceptCourseContent(courseId,adminId);
        if (done) {
            return Response.status(Response.Status.OK)
                    .entity("Course content has been accepted successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Failed to update course.")
                    .build();
        }
    }

    @PUT
    @Path("/rejectContent/{courseId}/{adminId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectCourseContent(@PathParam("courseId")Long courseId,@PathParam("adminId") Long adminId) {
        boolean done = courseService.rejectCourseContent(courseId,adminId);
        if (done) {
            return Response.status(Response.Status.OK)
                    .entity("Course content has been rejected successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Failed to update course.")
                    .build();
        }
    }

    @DELETE
    @Path("/deleteCourse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCourse(DeleteCourseRequest request) {
        boolean deleted = courseService.deleteCourse(request.getCourseId(), request.getAdminId());
        if (deleted) {
            return Response.status(Response.Status.OK)
                    .entity("Course deleted successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course not found or unauthorized to delete.")
                    .build();
        }
    }

    @GET
    @Path("/getCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/getReviewedCourses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewedCourses() {
        List<Course> courses = courseService.getAllReviewedCourses();
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/getCourses/{instructorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByInstructor(@PathParam("instructorId") Long instructorId) {
        List<Course> courses = courseService.getCoursesByInstructor(instructorId);
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/searchCoursesByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCoursesByName(@PathParam("name") String name) {
        List<Course> courses = courseService.searchCoursesByName(name);
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/searchCoursesByCategory/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCoursesByCategory(@PathParam("category") String category) {
        List<Course> courses = courseService.searchCoursesByCategory(category);
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/sortByRatings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortByRatings() {
        List<Course> courses = courseService.sortByRatings();
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @GET
    @Path("/sortByRatingsInstructor/{instructorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortByRatingsInstructor(@PathParam("instructorId") Long instructorId) {
        List<Course> courses = courseService.sortByRatingsInstructor(instructorId);
        return Response.status(Response.Status.OK)
                .entity(courses)
                .build();
    }

    @PUT
    @Path("/rateCourse/{courseId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateCourse( @PathParam("courseId") Long courseId, @PathParam("rating") double rating) {

        boolean rated = courseService.rateCourse(courseId, rating);

        if (rated) {
            return Response.status(Response.Status.OK)
                    .entity("Course rated successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Failed to rate course.")
                    .build();
        }
    }

    @POST
    @Path("/addReview")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(Reviews review) {
        boolean added = courseService.addReview(review);
        if (added) {
            return Response.status(Response.Status.CREATED)
                    .entity("Review added successfully.")
                    .build();
        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Failed to add review.")
                    .build();
        }
    }
}