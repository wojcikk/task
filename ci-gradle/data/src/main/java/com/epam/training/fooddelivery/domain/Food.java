package com.epam.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Food {
    private final String name;
    private final BigDecimal calorie;
    private final String description;
    private final BigDecimal price;
    private final Category category;

    public Food(String name, BigDecimal calorie, String description, BigDecimal price, Category category) {
        this.name = name;
        this.calorie = calorie;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
                Objects.equals(calorie, food.calorie) &&
                Objects.equals(description, food.description) &&
                Objects.equals(price, food.price) &&
                category == food.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calorie, description, price, category);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCalorie() {
        return calorie;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
