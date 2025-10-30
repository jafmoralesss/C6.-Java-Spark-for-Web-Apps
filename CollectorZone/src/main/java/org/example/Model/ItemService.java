package org.example.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemService {

    private final Map<String, CollectibleItem> itemDatabase = new HashMap<>();

    public Collection<CollectibleItem> getAllItems(){
        return itemDatabase.values();
    }

    public Optional<CollectibleItem> getItemById (String id){
        return Optional.ofNullable(itemDatabase.get(id));
    }

    public CollectibleItem createItem(String id, CollectibleItem item){
        if (itemDatabase.containsKey(id)){
            return null;
        }

        item.setId(id);
        itemDatabase.put(id, item);
        return item;
    }

    public Optional<CollectibleItem> updateItem(String id, CollectibleItem item){
        if (!itemDatabase.containsKey(id)){
            return Optional.empty();
        }
        item.setId(id);
        itemDatabase.put(id, item);
        return Optional.of(item);
    }

    public boolean deleteItem (String id) {
        return itemDatabase.remove(id) != null;
    }

    public boolean itemExists(String id){
        return itemDatabase.containsKey(id);
    }
}
