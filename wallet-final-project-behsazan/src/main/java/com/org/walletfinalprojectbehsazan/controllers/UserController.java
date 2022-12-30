package com.org.walletfinalprojectbehsazan.controllers;
import com.org.walletfinalprojectbehsazan.Service.UserService;
import com.org.walletfinalprojectbehsazan.exception.UserServiceException;
import com.org.walletfinalprojectbehsazan.model.Dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userManagementService;
	
	@PutMapping("/updateUserProfile")
	public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails updateUserProfile(@RequestBody @Valid com.org.walletfinalprojectbehsazan.model.Dto.UserDetails userDetails) throws UserServiceException
	{
		return userManagementService.updateUserProfile(userDetails);
	}
	
	@GetMapping("/{username}")
	public UserDetails viewUserProfile(@PathVariable("username") String userName) throws UserServiceException
	{
		return userManagementService.getUserProfileByUserName(userName);
	}
}
