package org.example.Controller;

import org.example.Model.ItemService;
import org.example.Model.Offer;
import org.example.Model.OfferService;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.UUID;

public class OfferWebController {

    private final OfferService offerService;

    public OfferWebController(OfferService offerService) {
        this.offerService = offerService;
    }

    public String handleOfferForm(Request req, Response res) {
        String name = req.queryParams("name");
        String email = req.queryParams("email");
        double price = Double.parseDouble(req.queryParams("itemPrice"));
        String itemId  = req.queryParams("item-id");
        UUID id = UUID.randomUUID();

        Offer newOffer = new Offer(name, email, id, price, itemId, new Date());

        offerService.createOffer(newOffer);

        res.redirect("/items-web");
        return null;
    }

}
