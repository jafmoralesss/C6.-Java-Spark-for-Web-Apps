package org.example.Controller;

import org.example.Model.CollectibleItem;
import org.example.Model.ItemService;
import org.example.Model.Offer;
import org.example.Model.OfferService;
import org.example.dto.ItemWebResponse;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.*;

public class ItemWebController {

    private final ItemService itemService;
    private final OfferService offerService;
    private final MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

    public ItemWebController(ItemService itemService, OfferService offerService) {
        this.itemService = itemService;
        this.offerService = offerService;
    }

    /**
     * Handles GET /items-web
     * Shows the main page with the form and the list of items.
     */
    public String showItemsPage(Request req, Response res) {

        Map<String, Object> model = new HashMap<>();

        String error = req.queryParams("error");
        if (error != null && !error.isEmpty()) {
            model.put("error", error);
        }

        String success = req.queryParams("success");
        if (success != null && !success.isEmpty()) {
            model.put("success", success);
        }

        Collection<CollectibleItem> allItems = itemService.getAllItems();

        List<ItemWebResponse> itemsWeb = allItems.stream().map(i -> {
            ItemWebResponse itemWeb = new ItemWebResponse();
            itemWeb.setId(i.getId());
            itemWeb.setName(i.getName());
            itemWeb.setDescription(i.getDescription());
            itemWeb.setPrice(i.getPrice());
            Optional<Offer> lastOffer = offerService.getLastOffer(UUID.fromString(i.getId()));
            lastOffer.ifPresent(offer -> itemWeb.setLastOffer(offer.getPrice()));

            return itemWeb;
        }).toList();

        model.put("items", itemsWeb);

        ModelAndView mav = new ModelAndView(model, "items.mustache");


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
