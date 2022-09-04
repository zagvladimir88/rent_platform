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
        return new ResponseEntity<>(Collections.singletonMap("result", itemService.findAll()),
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
    public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {

        long itemId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("item", itemService.findById(itemId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody ItemCreateRequest createRequest) {

        Item item = new Item();
        item.setItem_name(createRequest.getItem_name());
        item.setItem_type_id(createRequest.getItem_type_id());
        item.setLocation_id(createRequest.getLocation_id());
        item.setItem_location(createRequest.getItem_location());
        item.setDescription(createRequest.getDescription());
        item.setOwner_id(createRequest.getItem_type_id());
        item.setPrice_per_hour(createRequest.getPrice_per_hour());
        item.setAvailable(createRequest.getAvailable());
        item.setCreation_date(new Timestamp(new Date().getTime()));
        item.setModification_date(new Timestamp(new Date().getTime()));

        itemService.create(item);

        List<Item> items = itemService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("item", item.getItem_name());
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItemsById(@PathVariable Long id){

        itemService.delete(id);

        List<Item> items = itemService.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("items", items);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
