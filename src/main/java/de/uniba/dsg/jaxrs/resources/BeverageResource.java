package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Path("/beverages")
@Produces(MediaType.APPLICATION_JSON)
public class BeverageResource {

    private static final DB database = new DB();  // Simulated in-memory DB

    @GET
    public Response getAllBottles(
            @QueryParam("minPrice") Double minPrice,
            @QueryParam("maxPrice") Double maxPrice,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("10") int limit
    ) {
        List<Bottle> bottles = database.getBottles();

        if (minPrice != null) {
            bottles = bottles.stream()
                    .filter(b -> b.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }

        if (maxPrice != null) {
            bottles = bottles.stream()
                    .filter(b -> b.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        int from = (page - 1) * limit;
        int to = Math.min(from + limit, bottles.size());
        if (from > bottles.size()) {
            return Response.ok(Collections.emptyList()).build();
        }

        return Response.ok(bottles.subList(from, to)).build();
        //return Response.ok(bottles).build();
    }

    @GET
    @Path("/{id}")
    public Response getBottleById(@PathParam("id") int id) {
        return database.getBottles().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .map(b -> Response.ok(b).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Bottle not found").build());
    }
    }
