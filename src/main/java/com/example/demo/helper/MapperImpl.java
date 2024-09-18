package com.example.demo.helper;

import com.example.demo.contract.Helper;
import com.example.demo.contract.Mapper;
import com.example.demo.model.LoginResponseDto;
import com.example.demo.model.PhoneDto;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.model.SignUpResponseDto;
import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MapperImpl implements Mapper {

    @Autowired
    Helper helper;

    @Autowired
    Encryptor encryptor;

    public LoginResponseDto mapLoginResponse(User user){
        LoginResponseDto response = new LoginResponseDto();
        response.setActive(user.getActive());
        response.setCreated(user.getCreated());
        response.setLastLogin(user.getLastLogin());
        response.setId(user.getUid());
        response.setToken(user.getToken());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(encryptor.decode(user.getPassword()));
        return response;
    }

    public List<PhoneDto> mapToPhoneList(List<Phones> phones){
        Optional<List<Phones>> list =Optional.of(phones);
        final List<PhoneDto> result = new ArrayList<>();
        list.ifPresent((l)-> {
            l.forEach((p) -> {
                result.add(new PhoneDto(
                        p.getNumber(),
                        p.getCitycode(),
                        p.getContrycode()));
            });
        });

        return result;
    }

    public User mapUser(SignUpRequestDto request){
        User user = new User();
        user.setUid(UUID.randomUUID().toString());
        user.setActive(true);
        user.setCreated(helper.getNow());
        user.setLastLogin(helper.getNow());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(encryptor.encode(request.getPassword()));
        user.setToken(helper.generateToken(request.getPassword()));
        return user;
    }

    public List<Phones> mapPhones(SignUpRequestDto request, int id){
        final List<Phones> result = new ArrayList<>();

        if(request.getPhones() != null) {
            List<PhoneDto> list = request.getPhones();
            list.forEach(p -> {
                Phones phones = new Phones();
                phones.setUserId(id);
                phones.setCitycode(p.getCitycode());
                phones.setNumber(p.getNumber());
                phones.setContrycode(p.getContrycode());
                result.add(phones);
             }
            );
        }

        return result;
    }

    public SignUpResponseDto mapSignUpResponse(User user){
        SignUpResponseDto response = new SignUpResponseDto();
        response.setActive(user.getActive());
        response.setCreated(user.getCreated());
        response.setLastLogin(user.getLastLogin());
        response.setId(user.getUid());
        response.setToken(user.getToken());

        return response;
    }
}
