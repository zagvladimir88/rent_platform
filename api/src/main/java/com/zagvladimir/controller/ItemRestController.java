package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.SearchRequest;
import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.domain.Item;
import com.zagvladimir.repository.SubItemTypeRepository;
import com.zagvladimir.service.ItemService;
import com.zagvladimir.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemRestController {

    private final ItemService itemService;
    private final LocationService locationService;
    private final SubItemTypeRepository subItemTypeRepository;

    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(itemService.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findAllItemsWithParams(@ModelAttribute SearchRequest searchRequest) {

        int verifiedLimit = Integer.parseInt(searchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(searchRequest.getOffset());

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
        item.setSubItemType(subItemTypeRepository.findById(createRequest.getItemTypeId()).get());
        item.setLocation(locationService.findById(createRequest.getLocationId()).get());
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
