package com.zagvladimir.controller;

import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

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
}
