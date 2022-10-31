package com.zagvladimir.controller;

import com.zagvladimir.controller.mappers.GradeMapper;
import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.controller.response.GradeResponse;
import com.zagvladimir.domain.Grade;
import com.zagvladimir.service.grade.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Grade controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {

  private final GradeService gradeService;
  private final GradeMapper gradeMapper;

  @Operation(
      summary = "Gets all grades",
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
  public ResponseEntity<Object> findAllGrades(@ParameterObject Pageable page) {
    Page<GradeResponse> gradeResponse = gradeService.findAll(page).map(gradeMapper::toResponse);
    return new ResponseEntity<>(Collections.singletonMap("Grades", gradeResponse), HttpStatus.OK);
  }

  @Operation(
      summary = "Gets grade by ID",
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
  public ResponseEntity<Map<String, Object>> findGradeById(@PathVariable Long id) {
    GradeResponse response = gradeMapper.toResponse(gradeService.findById(id));
    return new ResponseEntity<>(Collections.singletonMap("Grade", response), HttpStatus.OK);
  }

  @Operation(
      summary = "Create new grade",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "grade was created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GradeResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "grade not created, Conflict",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "grade not created, Illegal Arguments",
            content = @Content)
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
  @PostMapping
  @Transactional
  public ResponseEntity<Object> createGrade(
      @RequestBody @Valid GradeCreateRequest gradeCreateRequest) {

    Grade newGrade = gradeMapper.convertCreateRequest(gradeCreateRequest);
    Long userId = gradeCreateRequest.getUserId();
    Long itemId = gradeCreateRequest.getItemId();
    GradeResponse gradeResponse =
        gradeMapper.toResponse(gradeService.create(newGrade, userId, itemId));

    return new ResponseEntity<>(
        Collections.singletonMap("grade", gradeResponse), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Soft delete grade",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Status changed to deleted",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "grade not found", content = @Content)
      },
      parameters = {
        @Parameter(
            in = ParameterIn.HEADER,
            name = "X-Auth-Token",
            required = true,
            description = "JWT Token, can be generated in auth controller /auth")
      })
  @PreAuthorize("@gradeServiceImpl.getLoginWhoRatedByGradeId(#id).equals(principal.username) or hasAnyRole('ADMIN','MANAGER')")
  @PatchMapping("/{id}")
  public ResponseEntity<Object> softDeleteGradeById(@PathVariable String id) {
    Long gradeId = Long.parseLong(id);
    gradeService.softDelete(gradeId);
    return new ResponseEntity<>(
        Collections.singletonMap("The grade was deleted id", gradeId), HttpStatus.OK);
  }
}
