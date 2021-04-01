package fusikun.com.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.specificationSearch._SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    RoleService roleService;

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

    public List<User> findAll() {
        return userRepository.findAll();
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

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<UUID> getUserIdsBaseOnCenterIdAndRoleName(UUID centerId, String roleName) {
        Specification_User specification = new Specification_User();
        specification.add(new _SearchCriteria("center", SearchOperator.EQUAL, centerId, "id", ApiDataType.UUID_TYPE));
        specification.add(new _SearchCriteria("role", SearchOperator.EQUAL, roleName, "roleName",
                ApiDataType.STRING_TYPE));
        List<User> users = findAll(specification);
        return users.stream().map(User::getId).collect(Collectors.toList());
    }
}
