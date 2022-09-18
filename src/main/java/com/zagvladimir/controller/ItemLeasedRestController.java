package com.zagvladimir.controller;

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
        itemLeased.setItemId(createRequest.getItemId());
        itemLeased.setRenterId(createRequest.getRenterId());
        itemLeased.setTimeTo(createRequest.getTimeTo());
        itemLeased.setTimeFrom(createRequest.getTimeFrom());
        itemLeased.setPricePerHour(createRequest.getPricePerHour());
        itemLeased.setDiscount(createRequest.getDiscount());
        itemLeased.setFee(createRequest.getFee());
        itemLeased.setPriceTotal(createRequest.getPriceTotal());
        itemLeased.setRenterGradeDescription(createRequest.getRenterGradeDescription());
        itemLeased.setRentierGradeDescription(createRequest.getRentierGradeDescription());
        itemLeased.setCreationDate(new Timestamp(new Date().getTime()));
        itemLeased.setModificationDate(new Timestamp(new Date().getTime()));
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItemLeasedById(@PathVariable Long id){

        itemLeasedService.delete(id);

        Map<String, Object> model = new HashMap<>();
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
