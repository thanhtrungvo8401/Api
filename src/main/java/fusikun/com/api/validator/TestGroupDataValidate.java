package fusikun.com.api.validator;

import fusikun.com.api.dto.TestGroupRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.TestGroupService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.SpaceUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.validation.BindException;

import java.util.UUID;

@Component
public class TestGroupDataValidate {
    @Autowired
    TestGroupValidator validator;

    @Autowired
    UserService userService;

    @Autowired
    TestGroupService testGroupService;

    public final void validate(TestGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        // TRYM WHITE SPACE:
        req.setVocaCodes(SpaceUtils.trymWhiteSpace(req.getVocaCodes()));
        req.setName(SpaceUtils.trymWhiteSpace(req.getName()));
        req.setCorrects(SpaceUtils.trymWhiteSpace(req.getCorrects()));
        // VALIDATION:
        BindException errors = new BindException(req, TestGroupRequest.class.getName());
        validator.validate(req, errors);
        if (errors.hasErrors()) {
            throw new Ex_MethodArgumentNotValidException(
                    errors.getBindingResult());
        }
    }

    public final void validateOwnerNotExistByOwnerId(UUID id)
            throws NotFoundException {
        if (userService.findById(id) == null) {
            throw new NotFoundException("Owner with id =" + id + " is not existed");
        }
    }
    public final void validateTestGroupNotExistById(UUID id)
            throws NotFoundException {
        if (testGroupService.findById(id) == null) {
            throw new NotFoundException("TestGroup with id=" + id + " is not exist!");
        }
    }
}
