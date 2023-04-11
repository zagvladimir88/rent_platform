package com.zagvladimir.controller;

import com.zagvladimir.service.category.CategoryService;
import com.zagvladimir.service.grade.GradeService;
import com.zagvladimir.service.item.ItemService;
import com.zagvladimir.service.item_leased.ItemLeasedService;
import com.zagvladimir.service.role.RoleService;
import com.zagvladimir.service.sub_category.SubCategoryService;
import com.zagvladimir.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @Operation(summary = "confirmation of user orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "confirmation was successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    @PatchMapping("/confirm/{clientId}")
    public ResponseEntity<Map<String, Object>> confirmItemBooking(
            @PathVariable("clientId") String clientId) throws MessagingException {
        Long userID = Long.parseLong(clientId);
        userService.confirmItemBooking(userID);
        return new ResponseEntity<>(
                Collections.singletonMap("user", "Confirm successfully"), HttpStatus.OK);
    }


    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        userService.delete(userId);
        return new ResponseEntity<>(
                Collections.singletonMap("The user was deleted, id:", userId), HttpStatus.OK);
    }

    @Operation(summary = "Delete sub category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "sub category deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "sub category not found", content = @Content)})
    @DeleteMapping("/sub-categories/{id}")
    public ResponseEntity<Object> deleteSubCategoryById(@PathVariable String id) {
        Long subCategoryId = Long.parseLong(id);
        subCategoryService.delete(subCategoryId);
        return new ResponseEntity<>(
                Collections.singletonMap("The sub category was deleted, id:", subCategoryId), HttpStatus.OK);
    }

    @Operation(summary = "Delete itemLeased")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "itemLeased was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "itemLeased not found", content = @Content)})
    @DeleteMapping("/items-leased/{id}")
    public ResponseEntity<Object> deleteItemLeasedById(@PathVariable String id) {
        Long itemLeasedId = Long.parseLong(id);
        itemLeasedService.delete(itemLeasedId);
        return new ResponseEntity<>(
                Collections.singletonMap("The location was deleted, id:", itemLeasedId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)})
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Object> deleteItemsById(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        itemService.delete(itemId);
        return new ResponseEntity<>(
                Collections.singletonMap("The item was deleted, id:", itemId), HttpStatus.OK);
    }

    @Operation(summary = "Delete grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "grade was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "grade not found", content = @Content)})
    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Object> deleteGradeById(@PathVariable String id) {
        Long gradeId = Long.parseLong(id);
        gradeService.delete(gradeId);

        return new ResponseEntity<>(Collections.singletonMap("The grade was deleted, id:", gradeId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Category")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Item Category was deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Item Category not found", content = @Content)})
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteItemCategoryById(@PathVariable String id) {
        Long categoryId = Long.parseLong(id);
        categoryService.delete(categoryId);

        return new ResponseEntity<>(
                Collections.singletonMap("The category was deleted, id:", categoryId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)})
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable String id) {
        Long roleId = Long.parseLong(id);
        roleService.delete(roleId);
        return new ResponseEntity<>(
                Collections.singletonMap("role has been deleted id:", roleId), HttpStatus.OK);
    }
}
