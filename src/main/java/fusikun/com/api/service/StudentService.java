package fusikun.com.api.service;

import fusikun.com.api.dtoREQ.StudentRequest;
import fusikun.com.api.dtoREQ.UserRequest;
import fusikun.com.api.dtoRES.UserResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.validator.UserDataValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class StudentService {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CenterService centerService;

    @Autowired
    UserDataValidate userDataValidate;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse _createStudent(StudentRequest req)
            throws Ex_MethodArgumentNotValidException {

        Role roleStudent = roleService.findByRoleName(Constant.STUDENT_ROLE);
        req.setRoleId(roleStudent.getId());

        UserRequest userRequest = req.getUserRequest();
        if (userRequest.getCenterId() == null) {
            Center centerDefault = centerService.findByCenterName(Constant.CENTER_DEFAULT);
            userRequest.setCenterId(centerDefault.getId());
        }

        userDataValidate.validate(userRequest);
        User user = userRequest.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new UserResponse(userService.save(user));
    }

    public UserResponse _getMyProfile() {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return new UserResponse(
                userService.findById(jwtUserDetails.getId())
        );
    }
}
