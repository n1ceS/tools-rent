package pl.marczuk.toolsrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczuk.toolsrent.exception.BadRequestException;
import pl.marczuk.toolsrent.exception.ResourceNotFoundException;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.PriceList;
import pl.marczuk.toolsrent.payload.ApiResponse;
import pl.marczuk.toolsrent.repository.ItemRepository;
import pl.marczuk.toolsrent.repository.PriceListRepository;
import pl.marczuk.toolsrent.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final PriceListRepository priceListRepository;

    public ItemServiceImpl(ItemRepository itemRepository, PriceListRepository priceListRepository) {
        this.itemRepository = itemRepository;
        this.priceListRepository = priceListRepository;
    }

    @Override
    @Transactional
    public Item addItem(Item item) {
        if(itemRepository.existsByName(item.getName()))
        {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Item with name " + item.getName() + " exists.");
            throw new BadRequestException(apiResponse);
        }
        item.setName(item.getName().toUpperCase());
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public Item getItemByName(String name) {
        Item item = getItem(name, "Item");
        return item;
    }

    @Override
    @Transactional
    public Item editItem(Item item) {
        Item itemToEdit = getItem(item.getName(), "item");
        itemToEdit.setDescription(item.getDescription());
        PriceList priceList = priceListRepository.findByItem(itemToEdit).orElseThrow(() -> new ResourceNotFoundException("Price", "itemName", item.getName()));
        priceList.setPrice(item.getPriceList().getPrice());
        return itemToEdit;
    }

    private Item getItem(String name, String item) {
        return itemRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("item", "name", name));
    }
}
