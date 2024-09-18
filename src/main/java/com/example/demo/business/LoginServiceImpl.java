package com.example.demo.business;

import com.example.demo.contract.Helper;
import com.example.demo.contract.LoginService;
import com.example.demo.contract.Mapper;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.helper.Constants;
import com.example.demo.model.LoginResponseDto;
import com.example.demo.persistance.PhoneRepository;
import com.example.demo.persistance.UserRepository;
import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    Helper helper;

    @Autowired
    Mapper mapper;
    @Override
    public LoginResponseDto login(String token) throws InvalidTokenException {
        User user = userRepository.findByToken(token);

        if(user == null){
            throw new InvalidTokenException(Constants.TOKEN_ERROR);
        }

        LoginResponseDto response = mapper.mapLoginResponse(user);
        List<Phones> phones = phoneRepository.findByUserId(user.getId());
        response.setPhones(mapper.mapToPhoneList(phones));

        //actualizamos lastLogin y el token
        user.setLastLogin(helper.getNow());
        user.setToken(helper.generateToken(user.getPassword()));
        response.setToken(user.getToken());

        userRepository.save(user);
        return response;
    }


}
