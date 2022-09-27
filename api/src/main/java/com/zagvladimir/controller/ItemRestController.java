package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.controller.requests.items.ItemSearchRequest;
import com.zagvladimir.domain.Item;
import com.zagvladimir.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/items")
public class ItemRestController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(itemService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllItemsWithParams(@ModelAttribute ItemSearchRequest itemSearchRequest) {

        int verifiedLimit = Integer.parseInt(itemSearchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(itemSearchRequest.getOffset());

        List<Item> items = itemService.search(verifiedLimit, verifiedOffset);

        Map<String, Object> model = new HashMap<>();
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByItemId(@PathVariable String id) {

        long itemId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("item", itemService.findById(itemId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody ItemCreateRequest createRequest) {

        Item item = new Item();
        item.setItemName(createRequest.getItemName());
        item.setItemTypeId(createRequest.getItemTypeId());
        item.setLocationId(createRequest.getLocationId());
        item.setItemLocation(createRequest.getItemLocation());
        item.setDescription(createRequest.getDescription());
        item.setOwner(createRequest.getOwner());
        item.setPricePerHour(createRequest.getPricePerHour());
        item.setAvailable(createRequest.getAvailable());
        item.setCreationDate(new Timestamp(new Date().getTime()));
        item.setModificationDate(new Timestamp(new Date().getTime()));
        item.setStatus(createRequest.getStatus());

        itemService.create(item);

        List<Item> items = itemService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("item", item.getItemName());
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemsById(@PathVariable Long id){

        itemService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("delete item id = ", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
