package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Error;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Configurable
public class UserController {

    private List<User> userList = new ArrayList<User>();
    private Logger logger = LoggerFactory.getLogger(RsController.class);

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user){
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());
        userDto.setPhone(user.getPhone());
        userDto.setVoteNum(user.getVoteNum());
        try {
            userRepository.save(userDto);
        }catch (Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        UserDto userDto = userRepository.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity getUserList() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/userDelete/{id}")
    public ResponseEntity deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        Error error = new Error();
        String message = "invalid user";
        error.setError(message);
        logger.error(message);
        return ResponseEntity.badRequest().body(error);
    }
}
