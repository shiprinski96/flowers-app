package org.acme.controller;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.entity.Flower;
import org.acme.entity.Area;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Path("/area")
public class AreaController {

    private static final Logger logger = Logger.getLogger(AreaController.class);

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") long id) {
        PanacheEntityBase area = Area.findById(id);

        if (area != null) {
            return Response.ok(area).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces("application/json")
    public Response getAll() {
        List<HashMap<String, Object>> areas = Area.getAreas();

        return Response.ok(areas).build();
    }

    @POST
    @Transactional
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Area area) {
        logger.info(area.toString());

        if (area.name != null && area.id == 0) {
            area.persist();

            return Response.created(URI.create("/area/" + area.id))
                    .build();
        }

        return Response.noContent().build();
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @Produces("application/json")
    public Response update(@PathParam("id") long id, Area area) {
        logger.info(area.toString());

        Area areaEntity = Area.findById(id);
        if (area != null && areaEntity != null) {

            areaEntity.name = area.name;
            areaEntity.persist();

            logger.info(area.toString());

            return Response.status(201).build();
        }

        return Response.status(204).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Produces("application/json")
    public Response delete(@PathParam("id") long id) {
        PanacheEntityBase area = Area.findById(id);

        if (area != null) {
            ((Area) area).flowers.forEach(f -> {
                logger.info(f);
                f.delete();
            });
            area.delete();

            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
