package fusikun.com.api.validator;

import fusikun.com.api.dto.RememberGroupRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.RememberGroupService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.SpaceUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.UUID;

@Component
public class RememberGroupDataValidate {
    @Autowired
    RememberGroupValidator rememberGroupValidator;

    @Autowired
    UserService userService;

    @Autowired
    RememberGroupService rememberGroupService;

    public final void validate(RememberGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        // TRYM WHITE SPACE:
        req.setVocaCodes(SpaceUtils.trymWhiteSpace(req.getVocaCodes()));
        req.setActiveCodes(SpaceUtils.trymWhiteSpace(req.getActiveCodes()));
        req.setName(SpaceUtils.trymWhiteSpace(req.getName()));
        // VALIDATION:
        BindException errors = new BindException(req, RememberGroupRequest.class.getName());
        rememberGroupValidator.validate(req, errors);
        if (errors.hasErrors()) {
            throw new Ex_MethodArgumentNotValidException(
                    errors.getBindingResult());
        }
    }

    public final void validateOwnerNotExistByOwnerId(UUID id) throws NotFoundException {
        if (userService.findById(id) == null) {
            throw new NotFoundException("Owner with id=" + id + " is not existed");
        }
    }

    public final void validateRemGroupNotExistById(UUID id) throws NotFoundException {
        if (rememberGroupService.findById(id) == null) {
            throw new NotFoundException("RememberGroup with id=" + id + " is not existed");
        }
    }
}
