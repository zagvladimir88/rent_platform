package com.zagvladimir.controller;

import com.zagvladimir.service.category.CategoryService;
import com.zagvladimir.service.grade.GradeService;
import com.zagvladimir.service.item.ItemService;
import com.zagvladimir.service.item_leased.ItemLeasedService;
import com.zagvladimir.service.role.RoleService;
import com.zagvladimir.service.sub_category.SubCategoryService;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Admin controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final UserService userService;
  private final SubCategoryService subCategoryService;
  private final ItemService itemService;
  private final CategoryService categoryService;
  private final ItemLeasedService itemLeasedService;
  private final GradeService gradeService;
  private final RoleService roleService;

  @PatchMapping("/confirm/{clientId}")
  public ResponseEntity<Map<String, Object>> confirmItemBooking(
      @PathVariable("clientId") String clientId) throws MessagingException {
    Long userID = Long.parseLong(clientId);
    userService.confirmItemBooking(userID);
    return new ResponseEntity<>(
        Collections.singletonMap("user", "Confirm successfully"), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete user",
      responses = {
        @ApiResponse(responseCode = "200", description = "user was deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> deleteUsersById(@PathVariable Long id) {
    userService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The user was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete sub category",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "sub category deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "sub category not found",
            content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/sub-categories/{id}")
  public ResponseEntity<Object> deleteSubCategoryById(@PathVariable Long id) {
    subCategoryService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The sub category was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete itemLeased",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "itemLeased was deleted",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "itemLeased not found", content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/items-leased/{id}")
  public ResponseEntity<Object> deleteItemLeasedById(@PathVariable Long id) {
    itemLeasedService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The location was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete Item",
      responses = {
        @ApiResponse(responseCode = "200", description = "Item deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/items/{id}")
  public ResponseEntity<Object> deleteItemsById(@PathVariable Long id) {
    itemService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("The item was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete grade",
      responses = {
        @ApiResponse(responseCode = "200", description = "grade was deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "grade not found", content = @Content)
      },
  parameters = {
          @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                  required = true, description = "JWT Token, can be generated in auth controller /auth")
  })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/grades/{id}")
  public ResponseEntity<Object> deleteGradeById(@PathVariable Long id) {

    gradeService.delete(id);

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @Operation(
      summary = "Delete Category",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Item Category was deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Item Category not found",
            content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/categories/{id}")
  public ResponseEntity<Object> deleteItemCategoryById(@PathVariable Long id) {

    categoryService.delete(id);

    return new ResponseEntity<>(
        Collections.singletonMap("The category was deleted, id:", id), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete Role",
      responses = {
        @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
      },
          parameters = {
                  @Parameter(in = ParameterIn.HEADER, name = "X-Auth-Token",
                          required = true, description = "JWT Token, can be generated in auth controller /auth")
          })
  @PreAuthorize(value = "hasRole('ADMIN')")
  @DeleteMapping("/roles/{id}")
  public ResponseEntity<Object> deleteRoleById(@PathVariable Long id) {
    roleService.delete(id);
    return new ResponseEntity<>(
        Collections.singletonMap("role has been deleted id:", id), HttpStatus.OK);
  }
}
