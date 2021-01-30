package fusikun.com.api.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fusikun.com.api.dto.SetVocaRequest;
import fusikun.com.api.exceptionHandlers.Ex_OverRangeException;
import fusikun.com.api.model.app.User;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.Specification_SetVoca;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.SpaceUtils;
import javassist.NotFoundException;

@Component
public class SetVocaDataValidate {
	@Autowired
	UserService userService;

	@Autowired
	SetVocaService setVocaService;

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

	public final void validateSetVocasOverRange(UUID authorId, Specification_SetVoca specification) {
		User author = userService.findById(authorId);
		if (author != null) {
			Integer maxSetVocas = author.getMaxSetVocas() != null ? author.getMaxSetVocas() : Constant.MAX_SET_VOCAS;
			Long currentSetVocas = setVocaService.count(specification);
			if (currentSetVocas >= maxSetVocas) {
				throw new Ex_OverRangeException("Can not create more than " + maxSetVocas + " in for this user");

			}
		}
	}
}
