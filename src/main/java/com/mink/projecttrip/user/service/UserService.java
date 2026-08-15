package com.mink.projecttrip.user.service;

import com.mink.projecttrip.common.SHA256HashingEncoder;
import com.mink.projecttrip.user.domain.User;
import com.mink.projecttrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean createUser(
            String nickName,
            String password,
            String name,
            String countryCode,
            String email){

        String encodePassword = SHA256HashingEncoder.encode(password);

        User user = User.builder()
                .countryCode(countryCode)
                .name(name)
                .nickName(nickName)
                .email(email)
                .password(encodePassword)
                .build();

        try{
            userRepository.save(user);
        }catch(DataAccessException e){
            return false;
        }
        return true;
    }

    public boolean isDuplicateNickName(String nickName){
        return userRepository.existsByNickName(nickName);
    }

    public boolean isDuplicateEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public User getUser(String email, String password){
        String encodePassword = SHA256HashingEncoder.encode(password);
        return userRepository.findByEmailAndPassword(email, encodePassword);
    }
}
