package util;

import requests.NotifyRequest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotifyMicroSvcUtil {

    private String notifMgmtBaseUrl = "http://notifysvc:8090";

    public boolean sendNotification(NotifyRequest notifyRequest)
    {
        Client client = ClientBuilder.newClient();
        String endpoint = "/send";
        String fullUrl = notifMgmtBaseUrl + endpoint;

        Response response = client.target(fullUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(notifyRequest));

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

}
