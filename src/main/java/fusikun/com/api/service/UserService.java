package fusikun.com.api.service;

import java.util.*;
import java.util.stream.Collectors;

import fusikun.com.api.dtoREQ.UserRequest;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.UserResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.specificationSearch.SearchHelpers_Users;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.SortHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dao.UserRepository;
import fusikun.com.api.model.app.User;
import fusikun.com.api.specificationSearch.Specification_User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CenterService centerService;

    public User save(User user) {
        if (user.getId() == null) {
            user.setCreatedDate(new Date());
        }
        if (user.getIsActive() == null) {
            user.setIsActive(true);
        }
        user.setUpdatedDate(new Date());

        return userRepository.save(user);
    }

    public Long count(Specification_User specification) {
        return userRepository.count(specification);
    }

    public Long count() {
        return userRepository.count();
    }

    public List<User> findAll(Specification_User specification, Pageable pageable) {
        Page<User> page = userRepository.findAll(specification, pageable);
        if (page.hasContent()) {
            return page.getContent();
        }
        return new ArrayList<>();
    }

    public List<User> findAll(Specification_User specification_user) {
        return userRepository.findAll(specification_user);
    }

    public User findById(UUID id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.orElse(null);
    }

    public User findByEmail(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        return optUser.orElse(null);
    }

    public List<User> findByRoleId(UUID roleId) {
        return userRepository.findByRoleId(roleId);
    }

    public Long countByEmail(String email) {
        return userRepository.countByEmail(email);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<UUID> _getUserIdsBaseOnCenterIdAndRoleName(UUID centerId, String roleName) {
        Specification_User specification = new Specification_User();
        specification.add(new _SearchCriteria("center", SearchOperator.EQUAL, centerId, "id", ApiDataType.UUID_TYPE));
        specification.add(new _SearchCriteria("role", SearchOperator.EQUAL, roleName, "roleName",
                ApiDataType.STRING_TYPE));
        List<User> users = findAll(specification);
        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    public UsersManagement _getUsersManagement(
            String filters,
            String limit,
            String page,
            String sortBy,
            String order
    ) {
        Specification_User specification = new SearchHelpers_Users(
                new Specification_User(),
                filters
        ).getSpecification(Arrays.asList(
                "id," + ApiDataType.UUID_TYPE,
                "role.id," + ApiDataType.UUID_TYPE,
                "role.roleName," + ApiDataType.STRING_TYPE,
                "email," + ApiDataType.STRING_TYPE,
                "center.centerName," + ApiDataType.STRING_TYPE
        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        Long total = count(specification);
        List<UserResponse> userResponses =
                findAll(specification, pageable)
                        .stream().map(UserResponse::new)
                        .collect(Collectors.toList());
        return new UsersManagement(userResponses, total);
    }

    public UserResponse _createUser(UserRequest req) {
        User user = req.getUser();
        if (req.getCenterId() == null) {
            Center center = centerService.findByCenterName(Constant.CENTER_DEFAULT);
            user.setCenter(center);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new UserResponse(save(user));
    }

    public UserResponse _getUserById(UUID id) {
        return new UserResponse(findById(id));
    }

    public UserResponse _updateUserById(UserRequest req, UUID id) {
        User oldUser = findById(id);
        User user = req.getUser();
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setMaxSetVocas(user.getMaxSetVocas());
        return new UserResponse(save(oldUser));
    }

    public UserResponse _deleteUserById(UUID id) {
        User user = findById(id);
        delete(user);
        return new UserResponse(user);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class UsersManagement extends ObjectsManagementList<UserResponse> {
        UsersManagement(List<UserResponse> list, Long total) {
            super(list, total);
        }
    }
}
