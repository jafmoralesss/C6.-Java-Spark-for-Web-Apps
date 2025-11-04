package org.example.Controller;

import com.sun.tools.attach.AttachOperationFailedException;
import org.example.Model.ItemService;
import org.example.Model.Offer;
import org.example.Model.OfferService;
import org.example.Model.ApiException;
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

        try{

            String name = req.queryParams("name");
            String email = req.queryParams("email");
            double price = Double.parseDouble(req.queryParams("itemPrice"));
            String itemId  = req.queryParams("item-id");
            UUID id = UUID.randomUUID();

            Offer newOffer = new Offer(name, email, id, price, itemId, new Date());

            offerService.createOffer(newOffer);

            res.redirect("/items-web?success=Offer submitted successfully!");


        } catch (ApiException e){
            res.redirect("/items-web?error=" + e.getMessage());
        } catch (NumberFormatException e) {
            res.redirect("/items-web?error=Invalid price format");
        }

        return null;
    }

}
