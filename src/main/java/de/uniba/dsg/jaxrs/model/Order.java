package de.uniba.dsg.jaxrs.model;

import java.util.List;
import java.util.Map;

public class Order {

    private int orderId;
    private List<OrderItem> positions;
    private double price;
    private OrderStatus status;
    private Map<String, String> links;

    public Order(int orderId, List<OrderItem> positions, double price, OrderStatus status) {
        this.orderId = orderId;
        this.positions = positions;
        this.price = price;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItem> positions) {
        this.positions = positions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public int getId() {
        return orderId;
    }

    public void setId(int id) {
        this.orderId = id;
    }

    public List<OrderItem> getItems() {
        return positions;
    }

    public void setItems(List<OrderItem> items) {
        this.positions = items;
    }

    public double getTotalPrice() {
        return price;
    }

    public void setTotalPrice(double price) {
        this.price = price;
    }


    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", positions=" + positions +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

}
