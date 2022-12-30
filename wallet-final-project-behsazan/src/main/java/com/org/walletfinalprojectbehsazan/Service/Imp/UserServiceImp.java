package com.org.walletfinalprojectbehsazan.Service.Imp;

import com.org.walletfinalprojectbehsazan.Dao.UserRepository;
import com.org.walletfinalprojectbehsazan.Service.UserService;
import com.org.walletfinalprojectbehsazan.exception.UserServiceException;
import com.org.walletfinalprojectbehsazan.model.Wallet;
import com.org.walletfinalprojectbehsazan.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        com.org.walletfinalprojectbehsazan.model.User user = userRepository.findByUserName(username);
        return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails createUserProfile(
            com.org.walletfinalprojectbehsazan.model.Dto.UserDetails useProfile) throws UserServiceException
    {
        try
        {

          com.org.walletfinalprojectbehsazan.model.User createdUser = userRepository
                    .save(MapperUtils.convertUserDetailsToUser(useProfile));
            return MapperUtils.convertUserToUserDetails(createdUser);
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails updateUserProfile(com.org.walletfinalprojectbehsazan.model.Dto.UserDetails useProfile) throws UserServiceException
    {
        try
        {
            com.org.walletfinalprojectbehsazan.model.User fetchedUser = userRepository.findByUserName(useProfile.getUserName());
            if(fetchedUser != null)
            {
                fetchedUser.setPassword(useProfile.getPassword());
                com.org.walletfinalprojectbehsazan.model.User updatedUser = userRepository.save(fetchedUser);
                return MapperUtils.convertUserToUserDetails(updatedUser);
            }
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @Override
    public void deleteUserProfileByUserName(String userName) throws UserServiceException
    {
        try
        {

            com.org.walletfinalprojectbehsazan.model.User user = userRepository.findByUserName(userName);
            if(user != null)
            {
                userRepository.delete(user);
            }
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails getUserProfileByUserName(String userName) throws UserServiceException
    {
        try
        {
           com.org.walletfinalprojectbehsazan.model.User user = userRepository.findByUserName(userName);
            return MapperUtils.convertUserToUserDetails(user);
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<com.org.walletfinalprojectbehsazan.model.Dto.UserDetails> getAllUserProfile() throws UserServiceException
    {
        try
        {
            List<com.org.walletfinalprojectbehsazan.model.User> findAll = userRepository.findAll();
            return MapperUtils.convertUserToUserDetails(findAll);
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public com.org.walletfinalprojectbehsazan.model.Dto.UserDetails createSignUp(com.org.walletfinalprojectbehsazan.model.User user) throws UserServiceException {

            try
            {
                user.setWallet(new Wallet(0)); //creating the user with wallet setup and amount 0
                com.org.walletfinalprojectbehsazan.model.User createdUser = userRepository.save(user);
                return MapperUtils.convertUserToUserDetails(createdUser);
            }catch (Exception e) {
                throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



    @Override
    public com.org.walletfinalprojectbehsazan.model.User getUserDbEntityByUserName(String userName) throws UserServiceException
    {
        try
        {
            return userRepository.findByUserName(userName);
        }catch (Exception e) {
            throw new UserServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
