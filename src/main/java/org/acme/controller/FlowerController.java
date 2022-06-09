package org.acme.controller;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.entity.Area;
import org.acme.entity.Store;
import org.acme.entity.Flower;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/flower")
public class FlowerController {
    private static final Logger logger = Logger.getLogger(FlowerController.class);

    @GET
    @Produces("application/json")
    public Response get() {
        List<Flower> flowers = Flower.listAll();
        return Response.ok(flowers).build();

    }

    @GET
    @Path("{query}")
    @Produces("application/json")
    public Response get(@PathParam("query") String query) {
        List<Flower> flowers = Flower.findByQuery(query);

        return Response.ok(flowers).build();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") long id) {
        PanacheEntityBase flower = Flower.findById(id);

        if (flower != null) {
            return Response.ok(flower).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Transactional
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Flower flower) {
        if (flower == null) {
            return Response.noContent().build();
        }

        logger.info(flower);
       	Store storeEntity = Store.findById(flower.store.id);
	    Area areaEntity = Area.findById(flower.area.id);

        if (storeEntity == null) {
            return Response.noContent().build();
        }

        if (areaEntity == null) {
            return Response.noContent().build();
        }

        flower.persist();
        return Response.created(URI.create("/flower/" + flower.id))
                .build();
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @Produces("application/json")
    public Response update(@PathParam("id") long id, Flower flower) {
        logger.info(flower.toString());

        Flower flowerEntity = Flower.findById(id);
        if (flower != null && flowerEntity != null) {
            flowerEntity.name = flower.name;
            flowerEntity.area = flower.area;
            return Response.status(201).build();
        }

        return Response.status(204).build();
    }


    @DELETE
    @Path("{id}")
    @Transactional
    @Produces("application/json")
    public Response delete(@PathParam("id") long id) {
        logger.info(id);

        PanacheEntityBase flowerEntity = Flower.findById(id);

        if (flowerEntity != null) {
            logger.info(flowerEntity.toString());

            flowerEntity.delete();
            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
