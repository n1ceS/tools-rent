package pl.marczuk.toolsrent.service;

import pl.marczuk.toolsrent.model.Item;

public interface ItemService {
    Item addItem(Item item);

    Item getItemByName(String name);

    Item editItem(Item item);
}
