package org.example;

import org.example.Controller.ItemController;
import org.example.Model.ItemService;

import static spark.Spark.*;

public class ApiService{

    public static void main(String[] args) {

        ItemService itemService = new ItemService();
        ItemController itemController = new ItemController(itemService);

        port(4567);

        get("/items", itemController::getAllItems);

        get("/items/:id", itemController::getItemById);

        post("/items/:id", itemController::createItem);

        put("/items/:id", itemController::updateItem);

        delete("/items/:id", itemController::deleteItem);

        options("/items/:id", itemController::checkItem);
    }
}
