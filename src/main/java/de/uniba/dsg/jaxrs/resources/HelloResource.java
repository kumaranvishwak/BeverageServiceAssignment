package de.uniba.dsg.jaxrs.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("")
public class HelloResource {

    @GET
    public Response sayHello(){
      return Response.ok("Hello folks :)").build();
    }


}
