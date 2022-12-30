package com.org.walletfinalprojectbehsazan.Service;

import com.org.walletfinalprojectbehsazan.exception.UserServiceException;
import com.org.walletfinalprojectbehsazan.model.Dto.UserDetails;
import com.org.walletfinalprojectbehsazan.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails createUserProfile(UserDetails useProfile) throws UserServiceException;
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails updateUserProfile(com.org.walletfinalprojectbehsazan.model.Dto.UserDetails useProfile) throws UserServiceException;
    public void deleteUserProfileByUserName(String userName) throws UserServiceException;
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails getUserProfileByUserName(String userName) throws UserServiceException;
    public List<com.org.walletfinalprojectbehsazan.model.Dto.UserDetails> getAllUserProfile() throws UserServiceException;
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails createSignUp(User user) throws UserServiceException;
    public User getUserDbEntityByUserName(String userName) throws UserServiceException;


}
