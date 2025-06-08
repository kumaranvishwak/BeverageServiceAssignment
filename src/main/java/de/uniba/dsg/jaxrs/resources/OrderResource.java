package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final DB database = new DB();  // In-memory DB

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") int id) {
        Optional<Order> order = database.getOrders().stream()
                .filter(o -> o.getId() == id)
                .findFirst();

        if (order.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order not found").build();
        }

        return Response.ok(order.get()).build();
    }

    @POST
    public Response submitOrder(Order order) {
        // Simple ID generation based on current size
        int newId = database.getOrders().size() + 1;
        order.setId(newId);
        order.setStatus(OrderStatus.SUBMITTED);

        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            if (item.getBeverage() instanceof Bottle) {
                Bottle b = (Bottle) item.getBeverage();
                b.setQuantity(b.getQuantity() - item.getQuantity());
                total += b.getPrice() * item.getQuantity();
            } else if (item.getBeverage() instanceof Crate) {
                Crate c = (Crate) item.getBeverage();
                c.setQuantity(c.getQuantity() - item.getQuantity());
                total += c.getPrice() * item.getQuantity();
            }
        }
        order.setTotalPrice(total);
        database.getOrders().add(order);

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOrder(@PathParam("id") int id, Order updatedOrder) {
        Optional<Order> existingOrderOpt = database.getOrders().stream()
                .filter(o -> o.getId() == id)
                .findFirst();

        if (existingOrderOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }

        Order existingOrder = existingOrderOpt.get();
        if (existingOrder.getStatus() != OrderStatus.SUBMITTED) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Order cannot be changed after processing").build();
        }

        existingOrder.setItems(updatedOrder.getItems());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
        return Response.ok(existingOrder).build();
    }

    @DELETE
    @Path("/{id}")
    public Response cancelOrder(@PathParam("id") int id) {
        Optional<Order> existingOrderOpt = database.getOrders().stream()
                .filter(o -> o.getId() == id)
                .findFirst();

        if (existingOrderOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }

        Order order = existingOrderOpt.get();
        if (order.getStatus() != OrderStatus.SUBMITTED) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Order already processed, cannot cancel").build();
        }

        database.getOrders().remove(order);
        return Response.noContent().build();
    }
}
