package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@RequestBody
                                                @Validated({UserDto.UserView.RegistrationPost.class})
                                                @JsonView({UserDto.UserView.RegistrationPost.class})
                                                UserDto userDto){

        if(service.findByUserName(userDto.getUserName()).isPresent()){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Username ja existente!");
        }

        if(service.findByEmail(userDto.getEmail()).isPresent()){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Ja existe um usuário com este email");
        }

        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreattionDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        var userModelResp = service.save(userModel);
        BeanUtils.copyProperties(userModelResp, userDto);


        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
