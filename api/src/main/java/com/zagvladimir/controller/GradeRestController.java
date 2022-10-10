package com.zagvladimir.controller;


import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.domain.Grade;
import com.zagvladimir.service.GradeService;
import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Gets all grades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the grades",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Grade.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllGrades() {
        return new ResponseEntity<>(
                Collections.singletonMap("Grades", gradeService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Gets grade by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the grade by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Grade.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findlGradeById(@PathVariable String id) {
        long gradeId = Long.parseLong(id);
        return new ResponseEntity<>(
                Collections.singletonMap("Grade", gradeService.findById(gradeId)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new grade",
            responses = {
                    @ApiResponse( responseCode = "201", description = "grade was created successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Grade.class))),
                    @ApiResponse( responseCode = "409", description = "grade not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "grade not created, Illegal Arguments", content = @Content)
            })
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

    @Operation(
            summary = "Delete grade",
            responses = {
                    @ApiResponse(responseCode = "200", description = "grade was deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "grade not found", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGradeById(@PathVariable Long id) {

        gradeService.delete(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
