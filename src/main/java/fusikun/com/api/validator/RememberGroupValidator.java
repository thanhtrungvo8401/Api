package fusikun.com.api.validator;

import fusikun.com.api.dto.RememberGroupRequest;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class RememberGroupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RememberGroupRequest.class);
    }

    @Autowired
    UserService userService;

    @Override
    public void validate(Object target, Errors errors) {
        RememberGroupRequest remGroupReq =
                (RememberGroupRequest) target;
        // check ownerId exist:
        if (userService.findById(remGroupReq.getOwnerId()) == null) {
            errors.rejectValue("ownerId", ConstantErrorCodes.NOT_FOUND);
        }
    }
}
