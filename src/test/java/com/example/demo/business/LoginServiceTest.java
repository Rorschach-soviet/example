package com.example.demo.business;

import com.example.demo.contract.LoginService;
import com.example.demo.contract.SignUpService;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.InvalidTokenException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.helper.Encryptor;
import com.example.demo.model.LoginResponseDto;
import com.example.demo.model.PhoneDto;
import com.example.demo.model.SignUpRequestDto;
import com.example.demo.persistance.PhoneRepository;
import com.example.demo.persistance.UserRepository;
import com.example.demo.persistance.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    Encryptor encryptor;

    @Test
    public void loginUser() {
        try {
            this.signUpService.signUp(getRequest());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        User user = this.userRepository.findById(1L).orElseThrow();
        LoginResponseDto login = null;

        try {
            login = this.loginService.login(user.getToken());
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(login);
        Assertions.assertEquals(user.getActive(),login.getActive());
        Assertions.assertEquals(user.getLastLogin(),login.getLastLogin());
        Assertions.assertEquals(user.getCreated(),login.getCreated());
        Assertions.assertEquals(user.getUid(),login.getId());
        Assertions.assertEquals(user.getName(),login.getName());
        Assertions.assertEquals(user.getEmail(),login.getEmail());
        Assertions.assertEquals(encryptor.decode(user.getPassword()),login.getPassword());
        this.userRepository.deleteAll();
        this.phoneRepository.deleteAll();
    }

    @Test
    public void failThrowingexception(){
        Assertions.assertThrows(InvalidTokenException.class,()->{
            this.loginService.login("a6s6s7s7a8a9s9a9a87a787a8a");
        });
    }

    private SignUpRequestDto getRequest(){
        SignUpRequestDto response = new SignUpRequestDto();
        response.setEmail("foobar@mymail.com");
        response.setName("John");
        response.setPassword("Fdelskdier23");
        response.setPhones(new ArrayList<>());
        response.getPhones().add(new PhoneDto(
                1234567,
                54,
                "AR"
        ));
        return response;
    }
}
