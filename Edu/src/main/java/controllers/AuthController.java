package controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import requests.UserRegisterationRequest;
import services.AuthService;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Stateless
@Api(value = "Auth service")

public class AuthController {

    private AuthService authService = new AuthService();

    @POST
    @Path("/register")
    @ApiOperation(value = "Retrieve some example content", notes = "Return some json to the client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserRegisterationRequest user) {
        try {
            if (authService.registerUser(user)) {
                return Response.status(Response.Status.CREATED).entity(user).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"User already exists!\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An error occurred during registration.\"}")
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            if (authService.loginUser(email, password)) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\": \"Login successful!\"}")
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Invalid email or password!\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An error occurred during login.\"}")
                    .build();
        }
    }


    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}