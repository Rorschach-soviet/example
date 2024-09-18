package com.example.demo.contract;

import com.example.demo.model.LoginResponseDto;
import com.example.demo.model.PhoneDto;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.model.SignUpResponseDto;
import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;

import java.util.List;

public interface Mapper {

    public LoginResponseDto mapLoginResponse(User user);
    public List<PhoneDto> mapToPhoneList(List<Phones> phones);

    public User mapUser(SignUpRequestDto request);

    public List<Phones> mapPhones(SignUpRequestDto request, int id);

    public SignUpResponseDto mapSignUpResponse(User user);
}
