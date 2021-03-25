package fusikun.com.api.validator;

import fusikun.com.api.dto.TestGroupRequest;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantErrorCodes;
import fusikun.com.api.utils.IsValidRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TestGroupValidator implements Validator {
    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TestGroupRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TestGroupRequest req = (TestGroupRequest) target;
        // check ownerId exist:
        if (userService.findById(req.getOwnerId()) == null) {
            errors.rejectValue("ownerId", ConstantErrorCodes.NOT_FOUND);
        }
        // validate vocaCodes:
        if (!IsValidRegex.vocaCodes(req.getVocaCodes())) {
            errors.rejectValue("vocaCodes", ConstantErrorCodes.INVALID_VALUE);
        }
    }
}
