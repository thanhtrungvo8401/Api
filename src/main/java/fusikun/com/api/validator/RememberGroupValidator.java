package fusikun.com.api.validator;

import fusikun.com.api.dto.RememberGroupRequest;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


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
        // validate vocaCodes:
        String patternStr = "^[1-9]{1}[0-9,]{1,}[0-9]{1}$";
        Pattern pattern = Pattern.compile(patternStr);
        boolean isValidVocaCodes = pattern.matcher(remGroupReq.getVocaCodes()).matches();
        if (!isValidVocaCodes || remGroupReq.getVocaCodes().contains(",,")) {
            errors.rejectValue("vocaCodes", ConstantErrorCodes.INVALID_VALUE);
        }
        // validate activeCodes:
        if (StringUtils.hasText(remGroupReq.getActiveCodes())) {
            boolean isValidActiveCodes = pattern.matcher(remGroupReq.getActiveCodes()).matches();
            if (!isValidActiveCodes || remGroupReq.getVocaCodes().contains(",,")) {
                errors.rejectValue("activeCodes", ConstantErrorCodes.INVALID_VALUE);
            }
        }
    }
}
