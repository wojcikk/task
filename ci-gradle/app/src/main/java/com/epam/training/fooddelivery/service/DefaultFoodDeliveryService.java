package com.epam.training.fooddelivery.service;

import com.epam.training.fooddelivery.data.DataStore;
import com.epam.training.fooddelivery.domain.*;

import java.math.BigDecimal;
import java.util.List;

public class DefaultFoodDeliveryService implements FoodDeliveryService {
    private final DataStore dataStore;

    public DefaultFoodDeliveryService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Customer authenticate(User user) {
        for(Customer customer: this.dataStore.getCustomers()) {
            if(customer.getEmail().equals(user.getEmail()) &&
            customer.getPassword().equals(user.getPassword())) {
                return customer;
            }
        }
        throw new AuthenticationException("Invalid data");
    }

    @Override
    public List<Food> listAllFood() {
        return this.dataStore.getFoods();
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) {
        addOrderItem(new OrderItem(pieces, new BigDecimal(pieces * food.getPrice().intValue()), food), customer.getCart());
        setPriceOfCart(customer.getCart().getOrderItems(), customer.getCart());
    }

    private void addOrderItem(OrderItem item, Cart cart) {
        if(item.getPieces() == 0) {
            deleteOrderItem(item, cart);
            return;
        }

        for(OrderItem orderItem : cart.getOrderItems()) {
            if(orderItem.getFood().getName().equals(item.getFood().getName())) {
                orderItem.setFood(item.getFood());
                orderItem.setPieces(item.getPieces());
                orderItem.setPrice(item.getPrice());
                return;
            }
        }

        cart.getOrderItems().add(item);
    }

    private void deleteOrderItem(OrderItem item, Cart cart) {
        int index = 0;

        for(OrderItem orderItem : cart.getOrderItems()) {
            if (orderItem.getFood().getName().equals(item.getFood().getName())) {
                cart.getOrderItems().remove(index);
                return;
            }
            index++;
        }
    }

    public void setPriceOfCart(List<OrderItem> items, Cart cart) {
        cart.setPrice(new BigDecimal(0));
        for(OrderItem item : items) {
            cart.setPrice(new BigDecimal(cart.getPrice().intValue() + item.getPrice().intValue()));
        }
    }

    @Override
    public Order createOrder(Customer customer) {
        Cart cart = customer.getCart();

        if(customer.getCart().getOrderItems().isEmpty()) {
            throw new IllegalStateException();
        }

        if (customer.getCart().getPrice().intValue() > customer.getBalance().intValue())
            throw new LowBalanceException("You don't have enough money, your balance is only " + customer.getBalance() + " EUR. We do not empty your cart, please remove some of the items.");


        Order order =  new Order();

        order.setCustomerId(customer.getId());
        order.setPrice(cart.getPrice());
        order.setOrderItems(customer.getCart().getOrderItems());

        customer.getOrders().add(order);

        return dataStore.createOrder(order);
    }
}
