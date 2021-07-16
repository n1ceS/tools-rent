package pl.marczuk.toolsrent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.marczuk.toolsrent.dto.ItemDto;
import pl.marczuk.toolsrent.model.Item;
import pl.marczuk.toolsrent.model.PriceList;
import pl.marczuk.toolsrent.service.ItemService;
import pl.marczuk.toolsrent.service.PriceListService;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final PriceListService priceListService;

    public ItemController(ItemService itemService, PriceListService priceListService) {
        this.itemService = itemService;
        this.priceListService = priceListService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto itemDto) {
        Item item = itemService.addItem(convertToEntity(itemDto));
        return new ResponseEntity<>(convertToDto(item), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemDto> editItem(@Valid @RequestBody ItemDto itemDto) {
        Item item = itemService.editItem(convertToEntity(itemDto));
        return new ResponseEntity<>(convertToDto(item), HttpStatus.OK);
    }

    private Item convertToEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPriceList(new PriceList(item, itemDto.getPrice()));
        return item;
    }

    private ItemDto convertToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription(item.getDescription());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPriceList().getPrice());
        return itemDto;
    }
}
