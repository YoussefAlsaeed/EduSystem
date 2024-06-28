package util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class UserMicroSvcUtil {

    private String userMgmtBaseUrl = "http://usersvc:8085";

    public boolean isUserInstructor(Long userId) {
        Client client = ClientBuilder.newClient();
        String endpoint = "/users/{type}/{id}";
        String fullUrl = userMgmtBaseUrl + endpoint;

        Response response = client.target(fullUrl)
                .resolveTemplate("type", UserType.Instructor)
                .resolveTemplate("id", userId)
                .request()
                .get();

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    public boolean isUserAdmin(Long userId) {
        Client client = ClientBuilder.newClient();
        String endpoint = "/users/{type}/{id}";
        String fullUrl = userMgmtBaseUrl + endpoint;

        Response response = client.target(fullUrl)
                .resolveTemplate("type", UserType.Admin)
                .resolveTemplate("id", userId)
                .request()
                .get();

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    public boolean isUserStudent(Long userId) {
        Client client = ClientBuilder.newClient();
        String endpoint = "/users/{type}/{id}";
        String fullUrl = userMgmtBaseUrl + endpoint;

        Response response = client.target(fullUrl)
                .resolveTemplate("type", UserType.Student)
                .resolveTemplate("id", userId)
                .request()
                .get();

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }


}
