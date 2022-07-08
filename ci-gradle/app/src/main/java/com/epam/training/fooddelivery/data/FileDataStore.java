package com.epam.training.fooddelivery.data;

import com.epam.training.fooddelivery.domain.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileDataStore implements DataStore {
    private final String baseDirPath;
    private static final String customersFilePath = "//customers.csv";
    private static final String ordersFilePath = "//orders.csv";
    private static final String foodsFilePath = "//foods.csv";
    private BufferedReader reader;
    private final List<Customer> customers;
    private final List<Food> foods;
    private final List<Order> orders;

    public FileDataStore(String baseDirPath) {
        this.baseDirPath = baseDirPath;
        this.customers = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public void init() {
        getCustomers();

        getFoods();

        getOrders();
    }

    @Override
    public List<Customer> getCustomers() {

        getReader(customersFilePath).lines().forEach(line -> {
            String[] lineElements = line.split(",");

            this.customers.add(new Customer(
                    lineElements[0],
                    lineElements[1],
                    Long.parseLong(lineElements[2]),
                    lineElements[3],
                    new BigDecimal(lineElements[4])
            ));
        });

        closeReader();

        return this.customers;
    }

    @Override
    public List<Food> getFoods() {

        getReader(foodsFilePath).lines().forEach(line -> {
            String[] lineElements = line.split(",");

            this.foods.add(new Food(
                    lineElements[0],
                    new BigDecimal(lineElements[1]),
                    lineElements[2],
                    new BigDecimal(lineElements[3]),
                    Category.valueOf(lineElements[4])
            ));
        });

        closeReader();

        return this.foods;
    }

    @Override
    public List<Order> getOrders() {

        getReader(ordersFilePath).lines().forEach(line -> {
            String[] lineElements = line.split(",");

            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(new OrderItem(
                    Integer.parseInt(lineElements[3]), // pieces
                    new BigDecimal(lineElements[4]), // price
                    getFoodByName(lineElements[2]) // Food
                    ));

            for(Order order: this.orders) {
                if(order.getOrderId() == Integer.parseInt(lineElements[0])) {
                    order.getOrderItems().add(new OrderItem(
                            Integer.parseInt(lineElements[3]),
                            new BigDecimal(lineElements[4]),
                            getFoodByName(lineElements[2])
                    ));
                    line = null;
                }
            }

            if(line != null) {
                this.orders.add(new Order(
                        Long.parseLong(lineElements[0]), //orderId
                        Long.parseLong(lineElements[1]), //customerId
                        new BigDecimal(lineElements[6]), //price
                        LocalDateTime.parse(lineElements[5], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), //timestampCreate,
                        orderItems));
            }
        });

        closeReader();

        return this.orders;
    }

    @Override
    public Order createOrder(Order order) {

        order.setOrderId((long) (this.orders.size() + 1));
        order.setTimestampCreated(LocalDateTime.now());
        this.orders.add(order);

        return order;
    }

    private Food getFoodByName(String name) {
        for(Food food: this.foods) {
            if(food.getName().equals(name)) {
                return food;
            }
        }
        System.out.println(name + " does not exist!");
        return null;
    }

    private BufferedReader getReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(baseDirPath + filePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reader;
    }

    private void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

