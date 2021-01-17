package fusikun.com.api.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fusikun.com.api.dto.VocaRequest;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.utils.SpaceUtils;
import javassist.NotFoundException;

@Component
public class VocaDataValidate {
	@Autowired
	SetVocaService setVocaService;

	public final void validate(VocaRequest vocaRequest) {
		// TRYM WHITE SPACE:
		vocaRequest.setVoca(SpaceUtils.trymWhiteSpace(vocaRequest.getVoca()));
		vocaRequest.setMeaning(SpaceUtils.trymWhiteSpace(vocaRequest.getMeaning()));
		vocaRequest.setKanji(SpaceUtils.trymWhiteSpace(vocaRequest.getKanji()));
		vocaRequest.setSentence(SpaceUtils.trymWhiteSpace(vocaRequest.getSentence()));
	}

	public final void validateExistSetVocaById(UUID setVocaId) throws NotFoundException {
		SetVoca setVoca = setVocaService.findById(setVocaId);
		if (setVoca == null) {
			throw new NotFoundException("Role with id=" + setVocaId + " is not existed!!");
		}
	}
}
