package org.acme.controller;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.entity.Flower;
import org.acme.entity.Store;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/store")
public class StoreController {
    private static final Logger logger = Logger.getLogger(StoreController.class);

    @GET
    @Produces("application/json")
    public Response get() {
        List<Store> stores = Store.listAll();

        return Response.ok(stores).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") long id) {
        PanacheEntityBase store = Store.findById(id);

        if (store != null) {
            return Response.ok(store).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Store store) {
        logger.info(store);
        PanacheEntityBase flower = Store.findById(store.id);

        if (store != null && flower != null) {
            store.persist();
            logger.info(store);
            return Response.created(URI.create("/store/" + store.id))
                    .build();
        }

        return Response.noContent().build();
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @Produces("application/json")
    public Response update(@PathParam("id") long id, Store store) {
        logger.info(store.toString());

        Store storeEntity = Store.findById(id);
        if (store != null && storeEntity != null) {
            storeEntity.address = store.address;
            storeEntity.name = store.name;

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

        PanacheEntityBase store = Store.findById(id);

        if (store != null) {
            logger.info(store.toString());

            store.delete();
            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}