package com.org.walletfinalprojectbehsazan.controllers;
import com.org.walletfinalprojectbehsazan.Service.UserService;
import com.org.walletfinalprojectbehsazan.model.Dto.UserAuthRequest;
import com.org.walletfinalprojectbehsazan.model.User;
import com.org.walletfinalprojectbehsazan.utils.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
		
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccessTokenUtil accessTokenUtil;

	@Autowired
	private UserService userManagementService;
	
	@PostMapping("/signup")
	public String signup(@RequestBody @Valid UserAuthRequest authenticationRequest) throws Exception
	{
		com.org.walletfinalprojectbehsazan.model.Dto.UserDetails createUser = userManagementService
				.createSignUp(new User(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		if(createUser != null)
		{
			return "SUCCESS, SignIn Now";
		}else
		{
			return "FAILED, Contact to the admin";
		}
	}
	
	@PostMapping("/signin")
	public String signin(@RequestBody @Valid UserAuthRequest authenticationRequest) throws Exception
	{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userManagementService
				.loadUserByUsername(authenticationRequest.getUsername());
		return accessTokenUtil.generateToken(userDetails);
	}

}
