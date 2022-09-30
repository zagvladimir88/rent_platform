package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.domain.Grade;
import com.zagvladimir.service.GradeService;
import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradeRestController {

    private final GradeService gradeService;
    private final ItemLeasedService itemLeasedService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> findAllGrades() {
        return new ResponseEntity<>(
                Collections.singletonMap("Grades", gradeService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findlGradeById(@PathVariable String id) {
        long gradeId = Long.parseLong(id);
        return new ResponseEntity<>(
                Collections.singletonMap("Grade", gradeService.findById(gradeId)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createGrade(@RequestBody GradeCreateRequest gradeCreateRequest) {

        Grade newGrade = new Grade();
        newGrade.setItemLeased(itemLeasedService.findById(gradeCreateRequest.getItemLeasedId()));
        newGrade.setUserFrom(userService.findById(gradeCreateRequest.getUserFromId()));
        newGrade.setUserTo(userService.findById(gradeCreateRequest.getUserToId()));
        newGrade.setDescription(gradeCreateRequest.getDescription());
        newGrade.setGrade(gradeCreateRequest.getGrade());
        newGrade.setCreationDate(new Timestamp(new Date().getTime()));
        newGrade.setModificationDate(new Timestamp(new Date().getTime()));
        newGrade.setStatus(gradeCreateRequest.getStatus());

        gradeService.create(newGrade);

        List<Grade> grades = gradeService.findAll();

        Map<String, Object> model = new HashMap<>();
        model.put("Grade", newGrade.getGrade());
        model.put("Grades", grades);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGradeById(@PathVariable Long id) {

        gradeService.delete(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
