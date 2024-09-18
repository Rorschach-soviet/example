package com.example.demo.business;

import com.example.demo.contract.Mapper;
import com.example.demo.contract.SignUpService;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.helper.Constants;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.model.SignUpResponseDto;
import com.example.demo.persistance.PhoneRepository;
import com.example.demo.persistance.UserRepository;
import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    Mapper mapper;
    @Override
    public SignUpResponseDto signUp(SignUpRequestDto request) throws UserAlreadyExistsException {
        if(userRepository.existsUserByEmail(request.getEmail())){
            throw new UserAlreadyExistsException(Constants.USER_EXISTS_ERROR);
        }
        User user = mapper.mapUser(request);
        int id = this.userRepository.save(user).getId();
        List<Phones> phones = mapper.mapPhones(request,id);

        phones.forEach((p)->{
            this.phoneRepository.save(p);
        });
        return mapper.mapSignUpResponse(user);
    }
}
