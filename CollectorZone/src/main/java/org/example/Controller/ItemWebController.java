package org.example.Controller;

import org.example.Model.CollectibleItem;
import org.example.Model.ItemService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemWebController {

    private final ItemService itemService;
    private final MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

    public ItemWebController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Handles GET /items-web
     * Shows the main page with the form and the list of items.
     */
    public String showItemsPage(Request req, Response res) {
        // 1. Create the model (a simple HashMap)
        Map<String, Object> model = new HashMap<>();

        // 2. Get all items from the service
        var allItems = itemService.getAllItems();

        // 3. Put the list of items and any other data into the model
        model.put("items", allItems);
        model.put("name", "Jafet"); // This fills {{name}} in the template

        // 4. Create a ModelAndView object
        // This tells Spark to use the "items.mustache" template
        ModelAndView mav = new ModelAndView(model, "items.mustache");

        // 5. Render the template and return the HTML
        return templateEngine.render(mav);
    }

    /**
     * Handles POST /items-web
     * Processes the "Add New Item" form.
     */
    public String handleItemForm(Request req, Response res) {
        String name = req.queryParams("itemName");
        String description = req.queryParams("itemDescription");
        String imageUrl = req.queryParams("itemImageUrl");
        double price = Double.parseDouble(req.queryParams("itemPrice"));


        String id = UUID.randomUUID().toString().substring(0, 8);
        CollectibleItem newItem = new CollectibleItem(id, name, description, price);

        itemService.createItem(id, newItem);


        res.redirect("/items-web");
        return null; // The redirect handles the response
    }
}
