package fusikun.com.api.controller;

import java.util.*;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoREQ.UserRequest;
import fusikun.com.api.dtoRES.UserResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.UserService;
import fusikun.com.api.validator.UserDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDataValidate userDataValidate;

    @GetMapping("/users")
    public ResponseEntity<ObjectsManagementList> handleGetUsers(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        return ResponseEntity
                .ok(userService._getUsersManagement(filters, limit, page, sortBy, order));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> handleCreateUser(@Valid @RequestBody UserRequest userRequest)
            throws Ex_MethodArgumentNotValidException {
        userDataValidate.validate(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService._createUser(userRequest));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) throws NotFoundException {
        userDataValidate.validateExistById(id);
        return ResponseEntity.ok(userService._getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> handleUpdateUserById(
            @Valid @RequestBody UserRequest userRequest,
            @PathVariable UUID id
    ) throws NotFoundException, Ex_MethodArgumentNotValidException {
        userRequest.setId(id);
        userDataValidate.validateExistById(id);
        userDataValidate.validate(userRequest);
        return ResponseEntity.ok(userService._updateUserById(userRequest, id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponse> handleDeleteUserById
            (@PathVariable UUID id) throws NotFoundException {
        userDataValidate.validateExistById(id);
        userDataValidate.validateNeverDeleteUser(id);
        userDataValidate.validateNotDeleteYourself(id);
        return ResponseEntity.ok(userService._deleteUserById(id));
    }
}
