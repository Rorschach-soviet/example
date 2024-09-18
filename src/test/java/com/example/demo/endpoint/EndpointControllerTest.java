package com.example.demo.endpoint;

import com.example.demo.contract.SignUpService;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.helper.Constants;
import com.example.demo.model.*;
import com.example.demo.persistance.UserRepository;
import com.example.demo.persistance.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EndpointControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SignUpService signUpService;

    @Autowired
    UserRepository userRepository;


    @Test
    public void singUpEndpointTest(){
        SignUpResponseDto response
                = this.restTemplate
                .postForObject("http://localhost:" + this.port + "/sign-up",getRequest(),SignUpResponseDto.class);
        Assertions.assertNotNull(response);
    }

    @Test
    public void failEmailEndpointTest(){
        SignUpRequestDto request = getRequest();
        request.setEmail(null);
        ErrorResponse response
                = this.restTemplate
                .postForObject("http://localhost:" + this.port + "/sign-up",request,ErrorResponse.class);
        Assertions.assertNotNull(response);
        ErrorDto error = response.getError().get(0);

        Assertions.assertEquals(Constants.REQUIRED_ERROR_CODE,error.getCodigo());
        Assertions.assertEquals(Constants.EMAIL_IS_REQUIRED,error.getDetail());
    }

    @Test
    public void failPasswordEndpointTest(){
        SignUpRequestDto request = getRequest();
        request.setPassword(null);
        ErrorResponse response
                = this.restTemplate
                .postForObject("http://localhost:" + this.port + "/sign-up",request,ErrorResponse.class);
        Assertions.assertNotNull(response);
        ErrorDto error = response.getError().get(0);

        Assertions.assertEquals(Constants.REQUIRED_ERROR_CODE,error.getCodigo());
        Assertions.assertEquals(Constants.PASSWORD_IS_REQUIRED,error.getDetail());
    }

    @Test
    public void failEmailFormatEndpointTest(){
        SignUpRequestDto request = getRequest();
        request.setEmail("jonhdoemymail.com");
        ErrorResponse response
                = this.restTemplate
                .postForObject("http://localhost:" + this.port + "/sign-up",request,ErrorResponse.class);
        Assertions.assertNotNull(response);
        ErrorDto error = response.getError().get(0);

        Assertions.assertEquals(Constants.FORMAT_ERROR_CODE,error.getCodigo());
        Assertions.assertEquals(Constants.EMAIL_ERROR,error.getDetail());
    }

    @Test
    public void failUserExistsEndpointTest() throws UserAlreadyExistsException, InvalidFormatException {
        SignUpRequestDto request = getRequest();
        request.setEmail("jonhdoe@mymail.com");

        this.signUpService.signUp(request);

        ErrorResponse response
                = this.restTemplate
                .postForObject("http://localhost:" + this.port + "/sign-up",request,ErrorResponse.class);
        Assertions.assertNotNull(response);
        ErrorDto error = response.getError().get(0);

        Assertions.assertEquals(Constants.USER_EXISTS_CODE,error.getCodigo());
        Assertions.assertEquals(Constants.USER_EXISTS_ERROR,error.getDetail());
    }

    @Test
    public void loginEndpointTest() throws UserAlreadyExistsException, InvalidFormatException {
        this.signUpService.signUp(getRequest());
        User user = this.userRepository.findById(1l).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ user.getToken());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        LoginResponseDto response =restTemplate.exchange("http://localhost:" + this.port + "/login",
                HttpMethod.GET, requestEntity, LoginResponseDto.class).getBody();

        Assertions.assertNotNull(response);
    }

    @Test
    public void failLoginEndpointTest() throws UserAlreadyExistsException, InvalidFormatException {
        this.signUpService.signUp(getRequest());
        User user = this.userRepository.findById(1l).orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ user.getToken()+"thisIsMySalt");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ErrorResponse response =restTemplate.exchange("http://localhost:" + this.port + "/login",
                HttpMethod.GET, requestEntity, ErrorResponse.class).getBody();

        ErrorDto error = response.getError().get(0);
        Assertions.assertNotNull(error);
        Assertions.assertEquals(Constants.TOKEN_ERROR_CODE,error.getCodigo());
        Assertions.assertEquals(Constants.TOKEN_ERROR,error.getDetail());
    }

    private SignUpRequestDto getRequest(){
        SignUpRequestDto response = new SignUpRequestDto();
        response.setEmail("foobar@mymail.com");
        response.setName("John");
        response.setPassword("Fdelskdier23");

        return response;
    }


    public void clearDb(){
        this.userRepository.deleteAll();
    }
}
