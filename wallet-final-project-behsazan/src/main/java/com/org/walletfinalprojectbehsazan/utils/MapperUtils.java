package com.org.walletfinalprojectbehsazan.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walletfinalprojectbehsazan.exception.UtilServiceException;
import com.org.walletfinalprojectbehsazan.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperUtils {

	public static com.org.walletfinalprojectbehsazan.model.Dto.UserDetails convertUserToUserDetails(User user) throws UtilServiceException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(user, com.org.walletfinalprojectbehsazan.model.Dto.UserDetails.class);
		}catch (Exception e) {
			throw new UtilServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static User convertUserDetailsToUser(com.org.walletfinalprojectbehsazan.model.Dto.UserDetails userDetails) throws UtilServiceException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(userDetails, User.class);
		}catch (Exception e) {
			throw new UtilServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static List<com.org.walletfinalprojectbehsazan.model.Dto.UserDetails> convertUserToUserDetails(List<User> user) throws UtilServiceException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(user, new TypeReference<List<com.org.walletfinalprojectbehsazan.model.Dto.UserDetails>>(){});
		}catch (Exception e) {
			throw new UtilServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static List<User> convertUserDetailsToUser(List<UserDetails> userDetails) throws UtilServiceException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(userDetails, new TypeReference<List<User>>(){});
		}catch (Exception e) {
			throw new UtilServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
