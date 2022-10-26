package com.zagvladimir.controller;


import com.zagvladimir.controller.mappers.GradeMapper;
import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.controller.response.GradeResponse;
import com.zagvladimir.domain.Grade;
import com.zagvladimir.service.grade.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Grade controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;
    private final GradeMapper gradeMapper;

    @Operation(summary = "Gets all grades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the grades",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = GradeResponse.class)))
                            })
            })
    @GetMapping
    public ResponseEntity<Object> findAllGrades() {
        List<GradeResponse> gradeResponseList = gradeService.findAll().stream().map(gradeMapper::toResponse).collect(Collectors.toList());
        return new ResponseEntity<>(
                Collections.singletonMap("Grades", gradeResponseList), HttpStatus.OK);
    }

    @Operation(summary = "Gets grade by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the grade by id",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = GradeResponse.class)))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findlGradeById(@PathVariable Long id) {
         return new ResponseEntity<>(
                Collections.singletonMap("Grade", gradeService.findById(id).map(gradeMapper::toResponse)), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new grade",
            responses = {
                    @ApiResponse( responseCode = "201", description = "grade was created successfully",content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GradeResponse.class))),
                    @ApiResponse( responseCode = "409", description = "grade not created, Conflict", content = @Content),
                    @ApiResponse( responseCode = "500", description = "grade not created, Illegal Arguments", content = @Content)
            })
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createGrade(@Valid @RequestBody GradeCreateRequest gradeCreateRequest) {

        Grade newGrade = gradeMapper.convertCreateRequest(gradeCreateRequest);
        Long userId = gradeCreateRequest.getUserId();
        Long itemId = gradeCreateRequest.getItemId();

        gradeService.create(newGrade, userId, itemId);

        return new ResponseEntity<>(gradeService.findById(newGrade.getId()).map(gradeMapper::toResponse), HttpStatus.CREATED);
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
