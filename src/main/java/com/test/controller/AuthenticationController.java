package com.test.controller;

import com.test.bean.User;
import com.test.repo.UserRepository;
import com.test.utility.GlobalService;
import com.test.utility.LoginRequest;
import com.test.utility.UserLoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GlobalService service;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity validateUser(@RequestBody LoginRequest user){
        System.out.println("login: "+user.toString());
        if(user.getEmail().isEmpty()){
            return service.getErrorResponse("Enter email!");
        }
        if(user.getUserType().equals(UserLoginType.normal)){
            if(user.getPassword().isEmpty()){
                return service.getErrorResponse("Enter password!");
            }
        }
        else if(user.getUserType().equals(UserLoginType.gmail)){
            User gmailUser = userRepo.findByEmail(user.getEmail());
            if(gmailUser == null){
                User newUser = new User();
                newUser.setEnabled(true);
                newUser.setEmail(user.getEmail());
                newUser.setUserType(UserLoginType.gmail);
                 userRepo.save(newUser);
                 return service.getSuccessResponse(userRepo.save(newUser));
            }else{
                 return service.getSuccessResponse(gmailUser);

            }
        }

        User db = userRepo.findByEmail(user.getEmail());

        if(db == null){
            return service.getSuccessResponse("There was a problem with your login!");
        }

        if(passwordEncoder.matches(user.getPassword() ,  db.getPassword())){
            return service.getSuccessResponse(db);

        }else{
            return service.getSuccessResponse("There was a problem with your login!");
        }



    }
}
