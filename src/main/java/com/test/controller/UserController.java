package com.test.controller;

import com.test.bean.User;
import com.test.repo.UserRepository;
import com.test.serviceImpl.UserService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user")
public class UserController {

        @Autowired
        private UserService service;

        @Autowired
        private GlobalService globalService;

        @Autowired
        private UserRepository userRepo;


        @PostMapping("/process_register")
        public ResponseEntity processRegister(@RequestBody User user, HttpServletRequest request)
                throws UnsupportedEncodingException, MessagingException {

            User userDb = userRepo.findByEmail(user.getEmail());

            if(userDb != null){
                return globalService.getErrorResponse("Email already exist! please try another one.");
            }

           Boolean flag =  service.register(user, getSiteURL(request));

           if(flag){
           return globalService.getSuccessResponse("Check email for verification");

           }
//            return "register_success";
           return globalService.getErrorResponse("Email not send");
        }



        private String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }

        @GetMapping("/verify")
        public ResponseEntity verifyUser(@RequestParam String code) {
            if (service.verify(code)) {
                return globalService.getSuccessResponse("Verified success");
            } else {
                return globalService.getErrorResponse("Error");
            }
        }
    }


