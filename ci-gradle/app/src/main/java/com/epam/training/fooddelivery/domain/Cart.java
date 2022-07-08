package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private BigDecimal price;
    private List<OrderItem> orderItems;

    public Cart() {
        this.price = new BigDecimal(0);
        this.orderItems = new ArrayList<>();
    }

    public BigDecimal getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "Cart{" +
                "price=" + price +
                ", orderItems=" + orderItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(price, cart.price) &&
                Objects.equals(orderItems, cart.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, orderItems);
    }
}
