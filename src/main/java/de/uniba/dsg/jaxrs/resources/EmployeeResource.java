package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.Optional;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private static final DB database = new DB();  // In-memory DB

    @POST
    @Path("/bottles")
    public Response addBottle(Bottle bottle) {
        bottle.setId(database.getBottles().size() + 1);
        database.getBottles().add(bottle);
        return Response.status(Response.Status.CREATED).entity(bottle).build();
    }

    @POST
    @Path("/crates")
    public Response addCrate(Crate crate) {
        crate.setId(database.getCrates().size() + 1);
        database.getCrates().add(crate);
        return Response.status(Response.Status.CREATED).entity(crate).build();
    }

    @PUT
    @Path("/bottles/{id}")
    public Response updateBottle(@PathParam("id") int id, Bottle updated) {
        Optional<Bottle> opt = database.getBottles().stream()
                .filter(b -> b.getId() == id).findFirst();

        if (opt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Bottle not found").build();
        }

        Bottle bottle = opt.get();
        bottle.setPrice(updated.getPrice());
        bottle.setQuantity(updated.getQuantity());
        return Response.ok(bottle).build();
    }

    @PUT
    @Path("/crates/{id}")
    public Response updateCrate(@PathParam("id") int id, Crate updated) {
        Optional<Crate> opt = database.getCrates().stream()
                .filter(c -> c.getId() == id).findFirst();

        if (opt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Crate not found").build();
        }

        Crate crate = opt.get();
        crate.setPrice(updated.getPrice());
        crate.setQuantity(updated.getQuantity());
        return Response.ok(crate).build();
    }

    @POST
    @Path("/orders/{id}/process")
    public Response processOrder(@PathParam("id") int id) {
        Optional<Order> opt = database.getOrders().stream()
                .filter(o -> o.getId() == id).findFirst();

        if (opt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }

        Order order = opt.get();
        if (order.getStatus() == OrderStatus.PROCESSED) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Order already processed").build();
        }

        order.setStatus(OrderStatus.PROCESSED);
        return Response.ok(order).build();
    }
}
