package org.example.Model;

import java.util.*;


public class OfferService {

    private static final Map<UUID, Offer> offerDatabase = new HashMap<>();

   
    public Collection<Offer> getAllOffers() {
        return offerDatabase.values();
    }

    public Offer getOfferById(UUID id) {
        Offer offer = offerDatabase.get(id);
        if (offer == null) {

            throw new ApiException(404, "Offer not found");
        }
        return offer;
    }

    public Offer createOffer(Offer offer) {

        offer.setId(UUID.randomUUID());
        offerDatabase.put(offer.getId(), offer);
        return offer;
    }

    public Offer updateOffer(UUID id, Offer offer) {
        if (!offerDatabase.containsKey(id)) {

            throw new ApiException(404, "Offer not found, cannot update");
        }
        offer.setId(id);
        offerDatabase.put(id, offer);
        return offer;
    }

    public void deleteOffer(UUID id) {
        if (offerDatabase.remove(id) == null) {

            throw new ApiException(404, "Offer not found");
        }

    }

    public void offerExists(UUID id) {
        if (!offerDatabase.containsKey(id)) {

            throw new ApiException(404, "Offer not found");
        }

    }
}
