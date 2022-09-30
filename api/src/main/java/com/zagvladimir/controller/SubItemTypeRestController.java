package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.sub_item_type.SubItemTypeCreateRequest;
import com.zagvladimir.domain.SubItemType;
import com.zagvladimir.repository.ItemCategoryRepository;
import com.zagvladimir.repository.SubItemTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sub-item-types")
public class SubItemTypeRestController {

    private final SubItemTypeRepository subItemTypeRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @GetMapping
    public ResponseEntity<Object> findAllISubItemTypes() {
        return new ResponseEntity<>(Collections.singletonMap("result", subItemTypeRepository.findAll()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findSubItemTypeById(@PathVariable String id) {
        long itemTypeCategoryId = Long.parseLong(id);
        return new ResponseEntity<>(Collections.singletonMap("role", subItemTypeRepository.findById(itemTypeCategoryId)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createSubItemType(@RequestBody SubItemTypeCreateRequest subItemTypeCreateRequest) {
        SubItemType subItemType = new SubItemType();

        subItemType.setSubCategoryName(subItemTypeCreateRequest.getSubCategoryName());
        subItemType.setItemCategory(itemCategoryRepository.findById(subItemTypeCreateRequest.getCategoryId()).get());
        subItemType.setCreationDate(new Timestamp(new Date().getTime()));
        subItemType.setModificationDate(subItemType.getCreationDate());
        subItemType.setStatus(subItemTypeCreateRequest.getStatus());

        subItemTypeRepository.save(subItemType);


        Map<String, Object> model = new HashMap<>();
        model.put("Sub_item_types",subItemTypeRepository.findById(subItemType.getId()).get());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubItemTypeById(@PathVariable Long id){

        subItemTypeRepository.deleteById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("Sub Item Category was deleted, id:", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
