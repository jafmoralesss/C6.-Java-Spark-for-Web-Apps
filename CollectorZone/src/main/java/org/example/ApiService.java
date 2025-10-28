package org.example;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class ApiService {

    private static Map<String, User> userDatabase = new HashMap<>();

    public static Gson gson = new Gson();

    public static void main (String[] args){

        port(4567);

        get("/users", (req, res) ->{
            res.type("application/json");
            return gson.toJson(userDatabase.values());
        });

        get("/users/:id", (req, res)->{
            res.type("application/json");
            String id = req.params(":id");
            User user = userDatabase.get(id);

            if (user != null) {
                return gson.toJson(user);
            } else {
                res.status(404);
                return gson.toJson(Map.of("error", "User not found."));
            }
        });

        post("/users/:id", (req, res) ->{
            res.type ("application/json");
            String id = req.params(":id");

            if (userDatabase.containsKey(id)){
                res.status(409);
                return gson.toJson(Map.of("error", "User with this ID already exists"));
            }

            try {
                User newUser = gson.fromJson(req.body(), User.class);
                newUser.setId(id);
                userDatabase.put(id, newUser);

                res.status(201);
                return gson.toJson(newUser);
            } catch (Exception e) {
                res.status(400);
                return gson.toJson(Map.of("error", "Invalid user data format"));
            }
        });

        put("/users/:id", (req, res) ->{
           res.type("application/json");
           String id = req.params(":id");

           if (!userDatabase.containsKey(id)){
               res.status(404);
               return gson.toJson(Map.of("error", "User not found, cannot update."));
           }

           try{
               User updatedUser= gson.fromJson(req.body(), User.class);
               updatedUser.setId(id);
               userDatabase.put(id, updatedUser);

               return gson.toJson(updatedUser);
           } catch (Exception e){
               res.status(400);
               return gson.toJson(Map.of("error", "Invalid user data format"));
           }
        });

        options("/users/:id", (req, res) ->{
            String id = req.params(":id");
            if (userDatabase.containsKey(id)) {
                res.status(200);
                return "User exists";
            } else {
                res.status(404);
                return "User not found";
            }
        });

        delete("/users/:id", (req, res) ->{
            res.type("application/json");
            String id = req.params(":id");

            User removedUser = userDatabase.remove(id);

            if (removedUser != null) {
                return gson.toJson(Map.of("message", "User deleted successfully"));
            } else {
                res.status(404);
                return gson.toJson(Map.of("error", "User not found"));
            }
        });


    }
}