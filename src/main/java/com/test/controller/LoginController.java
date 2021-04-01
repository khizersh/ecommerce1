package com.test.controller;

import com.test.bean.User;
import com.test.repo.UserRepository;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GlobalService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody User user) {
        if(user == null){
          return service.getErrorResponse("Please Provide Email and Password");
        }
        if(user.getEmail() == null){
            return service.getErrorResponse("Please Provide Email");

        }
        if(user.getPassword() == null){
            return service.getErrorResponse("Please Provide Password");

        }
        String email = user.getEmail();
        String password = user.getPassword();
        User userBean =  userRepository.findByEmail(email);

        if(userBean == null){
            return service.getErrorResponse("User Not Found");

        }
        if (userBean.getPassword()==null) {
            return service.getErrorResponse("The password is empty");
        }
        if(passwordEncoder.matches(password, userBean.getPassword())) {

            return service.getSuccessResponse(userBean);
        }

        return service.getErrorResponse("Wrong Credentials");
    }

}
