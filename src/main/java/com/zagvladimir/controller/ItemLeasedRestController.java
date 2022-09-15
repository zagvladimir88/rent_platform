package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.items.ItemCreateRequest;
import com.zagvladimir.controller.requests.itemsLeased.ItemLeasedCreateRequest;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.service.ItemLeasedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/items-leased")
public class ItemLeasedRestController {

    private final ItemLeasedService itemLeasedService;

    @GetMapping
    public ResponseEntity<Object> findAllItems() {
        return new ResponseEntity<>(Collections.singletonMap("result", itemLeasedService.findAll()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody ItemLeasedCreateRequest createRequest) {

        ItemLeased itemLeased = new ItemLeased();
        itemLeased.setItem_id(createRequest.getItem_id());
        itemLeased.setRenter_id(createRequest.getRenter_id());
        itemLeased.setTime_from(createRequest.getTime_from());
        itemLeased.setTime_to(createRequest.getTime_to());
        itemLeased.setPrice_per_hour(createRequest.getPrice_per_hour());
        itemLeased.setDiscount(createRequest.getDiscount());
        itemLeased.setFee(createRequest.getFee());
        itemLeased.setPrice_total(createRequest.getPrice_total());
        itemLeased.setRenter_grade_description(createRequest.getRenter_grade_description());
        itemLeased.setRentier_grade_description(createRequest.getRentier_grade_description());
        itemLeased.setCreation_date(new Timestamp(new Date().getTime()));
        itemLeased.setModification_date(new Timestamp(new Date().getTime()));
        itemLeased.setStatus(createRequest.getStatus());

        itemLeasedService.create(itemLeased);

        List<ItemLeased> items = itemLeasedService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("items", items);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findItemLeasedById(@PathVariable String id) {

        long itemId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("item", itemLeasedService.findById(itemId)), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteItemLeasedById(@PathVariable Long id){

        itemLeasedService.delete(id);

        List<ItemLeased> items = itemLeasedService.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("items", items);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
