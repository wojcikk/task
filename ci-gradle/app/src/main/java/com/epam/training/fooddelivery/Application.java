package com.epam.training.fooddelivery;

import com.epam.training.fooddelivery.data.FileDataStore;
import com.epam.training.fooddelivery.domain.Customer;
import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.User;
import com.epam.training.fooddelivery.service.BaseDirPath;
import com.epam.training.fooddelivery.service.DefaultFoodDeliveryService;
import com.epam.training.fooddelivery.service.FoodDeliveryService;
import com.epam.training.fooddelivery.service.LowBalanceException;
import com.epam.training.fooddelivery.view.CLIView;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }

    private void start() {
        FileDataStore dataStore = new FileDataStore(BaseDirPath.getBaseDirPath());
        dataStore.init();
        FoodDeliveryService service = new DefaultFoodDeliveryService(dataStore);
        CLIView view = new CLIView(dataStore);

        User user = view.readCredentials();

        Customer customer = service.authenticate(user);

        view.printWelcomeMessage(customer);

        Order order = new Order();

        Food food;
        int pieces;


        do {
            view.printAllFoods(service.listAllFood());

            food = view.selectFood(service.listAllFood());

            pieces = view.readPieces();

            service.updateCart(customer, food, pieces);

            view.printAddedToCart(food, pieces);

            view.printCart(customer.getCart());

            System.out.print("\nAre you finished with your order? (y/n): ");

        } while (view.readDecision());


        try {
            order = service.createOrder(customer);
        } catch (LowBalanceException e) {
            view.printErrorMessage(e.getMessage());
        }

        view.printConfirmOrder(order);
    }
}

