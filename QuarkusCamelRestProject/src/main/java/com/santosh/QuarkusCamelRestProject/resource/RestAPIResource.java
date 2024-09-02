package com.santosh.QuarkusCamelRestProject.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;

@Path("/santosh/camel-quarkus/rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestAPIResource {
	@Inject
    ProducerTemplate clientProcessingTemplate;
	@POST
    @Path("")
    public Response getResponse(String apiRequest, @Context HttpHeaders httpHeaders, @Context Request request) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("CamelHttpMethod", request.getMethod());
        // Get all headers
        MultivaluedMap<String, String> allHeaders = httpHeaders.getRequestHeaders();
        for (Map.Entry<String, List<String>> entry : allHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue().get(0));
        }
        Object responseObject = null;
        try {
            responseObject = clientProcessingTemplate.sendBodyAndHeaders("direct:camel-quarkus-rest",
                    ExchangePattern.InOut, apiRequest, headers);
        } catch (CamelExecutionException e) {
            return Response.status(Response.Status.OK)
                    .entity(responseObject).build();
        }
        return Response.status(Response.Status.OK)
                .entity(responseObject).build();

    }
}
