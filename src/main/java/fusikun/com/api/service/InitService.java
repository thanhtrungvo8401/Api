package fusikun.com.api.service;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InitService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CenterService centerService;

    public void initProject() {
        Role roleAdmin = new Role();
        Role roleStudent = new Role();
        Role roleAssistant = new Role();
        roleAdmin.setRoleName(Constant.ADMIN_ROLE);
        roleAdmin.setDescription("Full permission role");

        roleStudent.setRoleName(Constant.STUDENT_ROLE);
        roleStudent.setDescription("Common role!");

        roleAssistant.setRoleName(Constant.ASSISTANT_ROLE);
        roleAssistant.setDescription("Support admin role!");

        Center centerDefault = new Center();
        centerDefault.setCenterName(Constant.CENTER_DEFAULT);

        User admin = new User();
        admin.setEmail(Constant.EMAIL);
        admin.setPassword(passwordEncoder.encode(Constant.PASSWORD));
        admin.setRole(roleAdmin);
        admin.setCenter(centerDefault);

        roleService.save(roleAdmin);
        roleService.save(roleStudent);
        roleService.save(roleAssistant);

        centerService.save(centerDefault);

        userService.save(admin);
    }
}
