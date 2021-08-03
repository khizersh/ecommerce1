package com.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.User;
import com.test.bean.product.Product;
import com.test.repo.UserRepository;
import com.test.service.AmazonClient;
import com.test.serviceImpl.UserService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        @Autowired
        private AmazonClient amazonClient;


        @GetMapping
        public ResponseEntity getAllUsers() {
           return globalService.getSuccessResponse(userRepo.findByOrderByIdAsc());
        }

        @PostMapping("/verify")
        public ResponseEntity verifyUser(@RequestParam String code) {
            if (service.verify(code)) {
                return globalService.getSuccessResponse("Verified success");
            } else {
                return globalService.getErrorResponse("Error");
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity deleteUser(@PathVariable Long id) {
            if (id == null) {
                return globalService.getErrorResponse("Invalid request");
            } else {
                if(!userRepo.existsById(id)){
                return globalService.getErrorResponse("User not found!");
                }
                userRepo.deleteById(id);
                return globalService.getSuccessResponse("User removed successfully");
            }
        }

        @GetMapping("/sendVerificationCode/{email}")
        public ResponseEntity sendVerificationCode(@PathVariable String email , HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
            if(email == null){
                return globalService.getErrorResponse("Invalid request!");
            }
            User user = userRepo.findByEmail(email);


            boolean flag =  service.sendVerificationEmail(user , service.getSiteURL(request));

            if(flag){
                return globalService.getSuccessResponse("Verification code send successfully!");
            }
            return globalService.getErrorResponse("Something went wrong please try again later!");

        }


        @PostMapping("/process_register")
        public ResponseEntity processRegister(@RequestParam String user, @RequestParam(required = false) MultipartFile image , HttpServletRequest request)
                throws UnsupportedEncodingException, MessagingException, JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            User user1 = mapper.readValue(user, User.class);

            User userDb = userRepo.findByEmail(user1.getEmail());

            if(userDb != null){
                return globalService.getErrorResponse("Email already exist! please try another one.");
            }
            if(image != null){
              String photo =  amazonClient.uploadFile(image);
              user1.setUserImage(photo);
            }

           Boolean flag =  service.register(user1, service.getSiteURL(request));

           if(flag){
           return globalService.getSuccessResponse("Check email for verification");

           }
           return globalService.getErrorResponse("Email not send");
        }





    }


