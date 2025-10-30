package org.example.Controller;

import com.google.gson.Gson;
import org.example.Model.CollectibleItem;
import org.example.Model.ItemService;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.Optional;

public class ItemController {

    private final ItemService itemService;
    private final Gson gson = new Gson();

    public ItemController (ItemService itemService) {
        this.itemService = itemService;
    }

    public String getAllItems (Request req, Response res) {
        res.type("application/json");
        return gson.toJson(itemService.getAllItems());
    }

    public String getItemById (Request req, Response res){
        res.type("application/json");
        String id = req.params(":id");

        Optional<CollectibleItem> item = itemService.getItemById(id);

        if (item.isPresent()) {
            return gson.toJson(item.get());
        } else {
            res.status(404);
            return gson.toJson(Map.of("error", "Item not found"));
        }
    }

    public String createItem(Request req, Response res){
        res.type("application/json");
        String id = req.params(":id");

        try {
            CollectibleItem newItem = gson.fromJson(req.body(), CollectibleItem.class);
            CollectibleItem createdItem = itemService.createItem(id, newItem);

            if (createdItem == null) {
                res.status(409);
                return gson.toJson(Map.of("error", "Item with this ID already exists"));
            }

            res.status(201);
            return gson.toJson(createdItem);
        } catch (Exception e){
            res.status(400);
            return gson.toJson(Map.of("error", "Invalid item data format"));
        }
    }

    public String updateItem(Request req, Response res) {
        res.type("application/json");
        String id = req.params(":id");

        try {
            CollectibleItem itemToUpdate= gson.fromJson(req.body(), CollectibleItem.class);
            Optional<CollectibleItem> updatedItem = itemService.updateItem(id, itemToUpdate);

            if (updatedItem.isPresent()) {
                return gson.toJson(updatedItem.get());
            } else {
                res.status(404);
                return gson.toJson(Map.of("error", "Item not found, cannot update"));
            }
        } catch (Exception e) {
            res.status(400);
            return gson.toJson(Map.of("error", "Invalid item data format"));
        }
    }

    public String deleteItem(Request req, Response res){
        res.type("application/json");
        String id = req.params(":id");

        if (itemService.deleteItem(id)) {
            return gson.toJson(Map.of("message", "Item deleted successfully"));
        } else {
            res.status(404);
            return gson.toJson(Map.of("error", "Item not found"));
        }
    }

    public String checkItem (Request req, Response res) {
        String id= req.params(":id");
        if(itemService.itemExists(id)) {
            res.status(200);
            return "Item exists";
        } else {
            res.status(404);
            return "Item not found";
        }
    }
}
