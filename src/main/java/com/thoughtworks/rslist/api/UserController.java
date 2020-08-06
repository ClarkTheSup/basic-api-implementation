package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Error;
import com.thoughtworks.rslist.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = new ArrayList<User>();
    private Logger logger = LoggerFactory.getLogger(RsController.class);

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user){
        Integer index = null;
        if (!userList.contains(user)) {
            userList.add(user);
            index = userList.indexOf(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("index", String.valueOf(index)).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUserList() {
        return ResponseEntity.ok(userList);
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
