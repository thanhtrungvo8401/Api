package fusikun.com.api.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import fusikun.com.api.dtoREQ.RoleRequest;
import fusikun.com.api.dtoRES.AuthResponse;
import fusikun.com.api.dtoRES.MenuResponse;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.RoleResponse;
import fusikun.com.api.model.app.Auth;
import fusikun.com.api.model.app.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.RoleRepository;
import fusikun.com.api.model.app.Role;

@Service
@Transactional(rollbackOn = Exception.class)
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    AuthService authService;

    public void delete(Role role) {
        roleRepository.delete(role);
    }

    public Role findRoleById(UUID id) {
        Optional<Role> optRole = roleRepository.findById(id);
        return optRole.orElse(null);
    }

    public Role save(Role entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
        }
        entity.setUpdatedDate(new Date());
        if (entity.getIsActive() == null) {
            entity.setIsActive(true);
        }
        return roleRepository.save(entity);
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Long count() {
        return roleRepository.count();
    }

    public Boolean existByRoleName(String roleName) {
        return roleRepository.countByRoleName(roleName) > 0;
    }

    public RoleManagement _findRoleManagements() {
        List<Role> roles = findAll();
        List<RoleResponse> list = roles.stream().map(RoleResponse::new).collect(Collectors.toList());
        Long total = count();
        return new RoleManagement(list, total);
    }

    public RoleResponse _createRole(RoleRequest roleRequest) {
        Role role = roleRequest.getRole();
        Role savedRole = save(role);
        handleGenerateAuthsFromRoleAndMenus(savedRole, menuService.findAll());
        return new RoleResponse(savedRole);
    }

    public RoleResponse _getRoleById(UUID id) {
        Role role = findRoleById(id);
        List<Auth> auths = role.getAuths();
        List<String> menusMappedNames = auths
                .stream()
                .map(auth -> auth.getMenu().getName())
                .collect(Collectors.toList());
        List<Menu> menusNotMapped = !menusMappedNames.isEmpty()
                ? menuService.findNotMappedMenus(menusMappedNames)
                : menuService.findAll();
        if (!menusNotMapped.isEmpty()) {
            auths.addAll(handleGenerateAuthsFromRoleAndMenus(role, menusNotMapped));
        }
        List<AuthResponse> updatedAuthResponses = auths.stream()
                .map(auth -> new AuthResponse(auth, new MenuResponse(auth.getMenu())))
                .collect(Collectors.toList());
        RoleResponse roleRes = new RoleResponse(role);
        roleRes.setAuths(updatedAuthResponses);
        return roleRes;
    }

    public RoleResponse _updateRoleById(RoleRequest req, UUID id) {
        Role oldRole = findRoleById(id);
        oldRole.setRoleName(req.getRoleName());
        oldRole.setDescription(req.getDescription());
        return new RoleResponse(save(oldRole));
    }

    public RoleResponse _deleteRoleById(UUID id) {
        Role role = findRoleById(id);
        delete(role);
        return new RoleResponse(role);
    }

    private List<Auth> handleGenerateAuthsFromRoleAndMenus(Role role, List<Menu> menus) {
        List<Auth> auths = menus.stream().map(menu -> new Auth(role, menu, false)).collect(Collectors.toList());
        return authService.saveAll(auths);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class RoleManagement extends ObjectsManagementList<RoleResponse> {
        public RoleManagement(List<RoleResponse> list, Long total) {
            super(list, total);
        }
    }
}
