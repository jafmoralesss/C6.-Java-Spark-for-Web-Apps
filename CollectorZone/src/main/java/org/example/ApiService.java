package org.example;

import com.google.gson.Gson;
import org.example.Controller.ItemController;
import org.example.Controller.ItemWebController; // <-- IMPORT THIS
import org.example.Model.ApiError;
import org.example.Model.ApiException;
import org.example.Model.ItemService;

import static spark.Spark.*;

public class ApiService {

    public static void main(String[] args) {

        ItemService itemService = new ItemService();
        Gson gson = new Gson();

        ItemController itemController = new ItemController(itemService);

        ItemWebController itemWebController = new ItemWebController(itemService); // <-- CREATE NEW CONTROLLER

        port(4567);

        exception(ApiException.class, (exception, req, res) -> {
            res.status(exception.getStatusCode());
            res.type("application/json");
            res.body(gson.toJson(new ApiError(exception.getMessage())));
        });

        exception(Exception.class, (exception, req, res) -> {
            exception.printStackTrace();
            res.status(500);
            res.type("application/json");
            res.body(gson.toJson(new ApiError("An unexpected internal server error occurred.")));
        });

        get("/items", itemController::getAllItems);
        get("/items/:id", itemController::getItemById);
        post("/items/:id", itemController::createItem);
        put("/items/:id", itemController::updateItem);
        delete("/items/:id", itemController::deleteItem);
        options("/items/:id", itemController::checkItem);

        get("/items-web", itemWebController::showItemsPage); // <-- ADD THIS
        post("/items-web", itemWebController::handleItemForm); // <-- ADD THIS

    }
}