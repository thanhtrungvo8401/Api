package fusikun.com.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fusikun.com.api.dtoREQ.AuthRequest;
import fusikun.com.api.dtoRES.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dao.AuthRepository;
import fusikun.com.api.model.app.Auth;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    public Auth save(Auth entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
        }
        entity.setUpdatedDate(new Date());
        if (entity.getIsActive() == null) {
            entity.setIsActive(true);
        }
        return authRepository.save(entity);
    }

    public List<Auth> saveAll(List<Auth> auths) {
        auths.forEach(auth -> {
            if (auth.getId() == null) {
                auth.setCreatedDate(new Date());
            }
            if (auth.getIsActive() == null) {
                auth.setIsActive(true);
            }
            auth.setUpdatedDate(new Date());
        });
        return authRepository.saveAll(auths);
    }

    public Auth findById(UUID id) {
        Optional<Auth> authOpt = authRepository.findById(id);
        return authOpt.orElse(null);
    }

    public AuthResponse _updateAuthById(AuthRequest req, UUID id) {
        Auth oldAuth = findById(id);
        oldAuth.setIsActive(req.getIsActive());
        return new AuthResponse(save(oldAuth));
    }
}
