package fusikun.com.api.controller;

import javax.validation.Valid;

import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.specificationSearch.SearchHelpers_Users;
import fusikun.com.api.specificationSearch.Specification_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.dto.UserResponse;
import fusikun.com.api.dto.User_StudentRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.validator.UserDataValidate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author thtrungvo This controller is a copy of UserController, BUT: 1) Just
 * has some method for none login-user to create student. 2) RoleId is
 * not required, Its always Role-Student.
 */
@RestController
@RequestMapping("/api/common/v1")
public class StudentController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserDataValidate userDataValidate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/students")
    public ResponseEntity<Object> handleCreateStudent(@Valid @RequestBody User_StudentRequest studentRequest)
            throws Ex_MethodArgumentNotValidException {
        Role roleStudent = roleService.findByRoleName(Constant.STUDENT_ROLE);
        if (roleStudent != null) {
            studentRequest.setRoleId(roleStudent.getId());
        }
        UserRequest userRequest = studentRequest.getUserRequest();
        // USER VALIDATE:
        userDataValidate.validate(userRequest);
        User user = userRequest.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<Object> handleGetUserDetail() {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findById(jwtUserDetails.getId());
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/users/ids")
    public ResponseEntity<List<UUID>> handleGetUsersIdsList(
            @RequestParam(name = "filters", required = false) String filters
    ) {
        Specification_User userSpecification =
                new SearchHelpers_Users(new Specification_User(), filters)
                        .getSpecification(Arrays.asList(
                                "center.centerName," + ApiDataType.STRING_TYPE,
                                "role.roleName," + ApiDataType.STRING_TYPE
                        ));
        List<User> users = userService.findAll(userSpecification);
        List<UUID> ids = users.stream().map(User::getId).collect(Collectors.toList());
        return ResponseEntity.ok(ids);
    }
}
