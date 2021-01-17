package fusikun.com.api.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fusikun.com.api.dto.SetVocaRequest;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.SpaceUtils;
import javassist.NotFoundException;

@Component
public class SetVocaDataValidate {
	@Autowired
	UserService userService;
	public final void validate(SetVocaRequest setVocaRequest) {
		// TRYM WHITE SPACE:
		setVocaRequest.setSetName(SpaceUtils.trymWhiteSpace(setVocaRequest.getSetName()));
		// VALIDATE HERE:
	}
	
	public final void validateAuthorNotExistById(UUID id) throws NotFoundException {
		if (userService.findById(id) == null) {
			throw new NotFoundException("Author with id=" + id + "is not existed");
		}
	}
}
