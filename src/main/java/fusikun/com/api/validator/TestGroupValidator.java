package fusikun.com.api.validator;

import fusikun.com.api.dtoREQ.TestGroupRequest;
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
        if (!IsValidRegex.level_n_x(req.getMyVoca()) && !IsValidRegex.level_n_x_2(req.getMyVoca())) {
            errors.rejectValue("myVoca", ConstantErrorCodes.INVALID_VALUE);
        }
        if (!IsValidRegex.level_n_x(req.getN5()) && !IsValidRegex.level_n_x_2(req.getN5())) {
            errors.rejectValue("n5", ConstantErrorCodes.INVALID_VALUE);
        }
        if (!IsValidRegex.level_n_x(req.getN4()) && !IsValidRegex.level_n_x_2(req.getN4())) {
            errors.rejectValue("n4", ConstantErrorCodes.INVALID_VALUE);
        }
        if (!IsValidRegex.level_n_x(req.getN3()) && !IsValidRegex.level_n_x_2(req.getN3())) {
            errors.rejectValue("n3", ConstantErrorCodes.INVALID_VALUE);
        }
        if (!IsValidRegex.level_n_x(req.getN2()) && !IsValidRegex.level_n_x_2(req.getN2())) {
            errors.rejectValue("n2", ConstantErrorCodes.INVALID_VALUE);
        }
        if (!IsValidRegex.level_n_x(req.getN1()) && !IsValidRegex.level_n_x_2(req.getN1())) {
            errors.rejectValue("n1", ConstantErrorCodes.INVALID_VALUE);
        }
        // validate number:
        if(req.getNumber() < 0) {
            errors.rejectValue("number", ConstantErrorCodes.INVALID_VALUE);
        }
    }
}
