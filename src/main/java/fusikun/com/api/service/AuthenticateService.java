package fusikun.com.api.service;

import fusikun.com.api.dtoREQ.JwtRequest;
import fusikun.com.api.dtoRES.JwtResponse;
import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.model.app.User;
import fusikun.com.api.utils.ConstantMessages;
import fusikun.com.api.utils.JwtTokenUtil;
import fusikun.com.api.utils.RandomTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthenticateService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtResponse _validate_GenerateToken(JwtRequest req) {
        // authenticate userDate here => Error occurs => throw Exception and Stop
        authenticate(req.getEmail(), req.getPassword());

        User user = userService.findByEmail(req.getEmail());
        user.setAccessToken(RandomTokenUtil.generateToken(11));
        userService.save(user);

        JwtUserDetails userDetails = new JwtUserDetails(user, true);
        String jwt = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(jwt);
    }

    public String _logout() {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findById(jwtUserDetails.getId());
        user.setAccessToken(null);
        userService.save(user);
        return ConstantMessages.SUCCESS;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
