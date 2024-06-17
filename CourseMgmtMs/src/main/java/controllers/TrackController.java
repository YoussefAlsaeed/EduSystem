package controllers;

import io.swagger.annotations.Api;
import models.Enrollment;
import models.Logs;
import services.TrackService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/track")
@Stateless
@Api(value = "Tracking service")
public class TrackController {

    @Inject
    TrackService trackService;

    @GET
    @Path("/getUsage/{adminId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsage(@PathParam("adminId") Long adminId) {
        List<Logs> logs = trackService.getActivityUsage(adminId);
        return Response.status(Response.Status.OK)
                .entity(logs)
                .build();
    }
}
