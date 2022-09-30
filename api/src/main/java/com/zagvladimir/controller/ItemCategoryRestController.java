package com.zagvladimir.controller;

import com.zagvladimir.controller.requests.item_category.ItemCategoryCreateRequest;
import com.zagvladimir.domain.ItemCategory;
import com.zagvladimir.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item-categories")
public class ItemCategoryRestController {

  private final ItemCategoryRepository itemCategoryRepository;

  @GetMapping
  public ResponseEntity<Object> findAllItemCategories() {
    return new ResponseEntity<>(
        Collections.singletonMap("result", itemCategoryRepository.findAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> findItemCategoryById(@PathVariable String id) {
    long itemCategoryId = Long.parseLong(id);
    return new ResponseEntity<>(
        Collections.singletonMap("item category", itemCategoryRepository.findById(itemCategoryId)),
        HttpStatus.OK);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Object> createItemCategory(@RequestBody ItemCategoryCreateRequest itemCategoryCreateRequest) {
    ItemCategory newItemCategory = new ItemCategory();

    newItemCategory.setCategoryName(itemCategoryCreateRequest.getCategoryName());
    newItemCategory.setCreationDate(new Timestamp(new Date().getTime()));
    newItemCategory.setModificationDate(new Timestamp(new Date().getTime()));
    newItemCategory.setStatus(itemCategoryCreateRequest.getStatus());

    itemCategoryRepository.save(newItemCategory);

    List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
    Map<String, Object> model = new HashMap<>();
    model.put("item categories", itemCategoryList);

    return new ResponseEntity<>(model, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteItemCategoryById(@PathVariable Long id) {

    itemCategoryRepository.deleteById(id);

    Map<String, Object> model = new HashMap<>();
    model.put("role has been deleted id:", id);
    return new ResponseEntity<>(model, HttpStatus.OK);
  }
}
