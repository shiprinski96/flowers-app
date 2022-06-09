package org.acme;

import org.acme.entity.Area;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

//    @Path("test")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String test(@QueryParam("bloodType") String test)  {
//        Object o = new Object();
//        o.setBlodType(test);
//        return "Vseo pu4kom";
//    }
}