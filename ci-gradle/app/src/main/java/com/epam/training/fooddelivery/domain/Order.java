package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long orderId;
    private long customerId;
    private BigDecimal price;
    private LocalDateTime timestampCreated;
    private List<OrderItem> orderItems;

    public Order(Long orderId, long customerId, LocalDateTime timestampCreated) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.price = new BigDecimal(0);
        this.timestampCreated = timestampCreated;
        this.orderItems = new ArrayList<>();
    }

    public Order(Long orderId, long customerId, BigDecimal price, LocalDateTime timestampCreated, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.price = price;
        this.timestampCreated = timestampCreated;
        this.orderItems = orderItems;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", price=" + price +
                ", timestampCreated=" + timestampCreated +
                ", orderItems=" + orderItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return customerId == order.customerId &&
                Objects.equals(orderId, order.orderId) &&
                Objects.equals(price, order.price) &&
                Objects.equals(timestampCreated, order.timestampCreated) &&
                Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, price, timestampCreated, orderItems);
    }

    public Long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderItems(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setTimestampCreated(LocalDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTimestampCreatedFormat() {
        return timestampCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String printOrderedItems() {
        StringBuilder string = new StringBuilder();
        for(OrderItem orderItem : this.getOrderItems()) {
            string.append(orderItem.getFood().getName());
            string.append(", ");
        }
        return string.substring(0, string.length() - 2);
    }
}
