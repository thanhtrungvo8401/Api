package fusikun.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.model.JwtRequest;
import fusikun.com.api.model.JwtResponse;
import fusikun.com.api.model.JwtUserDetails;
import fusikun.com.api.model.User;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantMessages;
import fusikun.com.api.utils.JwtTokenUtil;
import fusikun.com.api.utils.RandomTokenUtil;

@RestController
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserService userService;

	@PostMapping("/authenticate/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		// authenticate userDate here => Error occurs => throw Exception and Stop
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		User user = userService.findByUsername(authenticationRequest.getUsername());
		user.setAccessToken(RandomTokenUtil.generateToken(11));
		userService.updateUser(user);

		final JwtUserDetails userDetails = new JwtUserDetails(user);
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@PostMapping("/authenticate/logout")
	public String handleLogout() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findByUsername(userDetails.getUsername());
		user.setAccessToken(null);
		userService.updateUser(user);
		return ConstantMessages.SUCCESS;
	}

	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
