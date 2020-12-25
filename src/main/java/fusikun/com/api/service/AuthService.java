package fusikun.com.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.AuthRepository;
import fusikun.com.api.model.Auth;

@Service
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
}
