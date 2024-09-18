package com.example.demo.business;

import com.example.demo.contract.SignUpService;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.helper.Encryptor;
import com.example.demo.model.PhoneDto;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.persistance.PhoneRepository;
import com.example.demo.persistance.UserRepository;
import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SignUpServiceTest {
    @Autowired
    SignUpService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    Encryptor encryptor;

    @Test
    public void signUpNewUser(){
        try {
            this.service.signUp(getRequest());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        User user = this.userRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(1,user.getId());
        Assertions.assertEquals("foobar@mymail.com",user.getEmail());
        Assertions.assertEquals(this.encryptor.encode("Fdelskdier23"),user.getPassword());
    }

    @Test
    public void signUpNewUserWithPhone(){
        SignUpRequestDto request = getRequest();
        request.setPhones(generatePhone());
        try {
            this.service.signUp(request);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        Phones phone = phoneRepository.findById(1L).orElseThrow();
        Assertions.assertNotNull(phone);
        Assertions.assertEquals(1234567,phone.getNumber());
        Assertions.assertEquals(54,phone.getCitycode());
        Assertions.assertEquals("AR",phone.getContrycode());
    }

    @Test
    public void failOnUserAlreadyExists(){
        Assertions.assertThrows(UserAlreadyExistsException.class,()->{
            this.service.signUp(getRequest());
            this.service.signUp(getRequest());
        });
    }

    private SignUpRequestDto getRequest(){
        SignUpRequestDto response = new SignUpRequestDto();
        response.setEmail("foobar@mymail.com");
        response.setName("John");
        response.setPassword("Fdelskdier23");

        return response;
    }

    private List<PhoneDto> generatePhone(){
        List<PhoneDto> result = new ArrayList<>();

        result.add(new PhoneDto(
                1234567,
                54,
                "AR"
        ));

        return result;
    }

    private void clearDb(){
        this.userRepository.deleteAll();
        this.phoneRepository.deleteAll();
    }
}
